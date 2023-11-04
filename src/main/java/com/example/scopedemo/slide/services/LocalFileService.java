package com.example.scopedemo.slide.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.scopedemo.common.exceptions.ServiceException;
import com.example.scopedemo.slide.domain.File;
import com.example.scopedemo.slide.dtos.SlideDto;
import com.example.scopedemo.slide.exceptions.FileDownloadFailedException;
import com.example.scopedemo.slide.exceptions.FileUploadFailedException;

@Service
public class LocalFileService implements FileService {
  private final Logger logger = LoggerFactory.getLogger(LocalFileService.class);
  private final Path fileStorageLocation;

  public LocalFileService() throws ServiceException {
    this.fileStorageLocation = Paths.get("files").toAbsolutePath().normalize();

    try {
      Files.createDirectories(this.fileStorageLocation);
    } catch (Exception e) {
      logger.error("File Storage Location: {}", this.fileStorageLocation);
      throw new FileUploadFailedException("Create Directories Error");
    }
  }

  @Override
  public SlideDto.UploadFileRes getFileData(final MultipartFile file) {
    String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
    String extension = "";
    String fileNameWithoutExt = originalFileName;

    int dotIndex = originalFileName.lastIndexOf(".");
    if (dotIndex >= 0) {
      extension = originalFileName.substring(dotIndex);
      fileNameWithoutExt = originalFileName.substring(0, dotIndex);
    }

    String fileName = fileNameWithoutExt + "-" + UUID.randomUUID() + extension;
    String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/").path(fileName)
        .toUriString();

    return new SlideDto.UploadFileRes(fileName, fileUrl, file.getSize());
  }

  @Async
  public void upload(final MultipartFile file, final String fileName) throws ServiceException {
    Path targetLocation = this.fileStorageLocation.resolve(fileName);
    try {
      logger.info("FILE UPLOAD START!!");
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
      logger.info("FILE UPLOAD END!!");
    } catch (Exception e) {
      logger.error("File Copy Error. fileName: {}, targetLocation: {}", fileName, targetLocation);
      throw new FileUploadFailedException("File Copy Error" + e.getMessage());
    }
  }

  @Override
  public byte[] download(final File file) throws ServiceException {
    String fileName = file.getName();

    try {
      Path path = Paths.get(this.fileStorageLocation.resolve(fileName).normalize().toString());
      return Files.readAllBytes(path);
    } catch (java.io.IOException e) {
      logger.error("File Download Error. fileName: {}", fileName);
      throw new FileDownloadFailedException("File Download Error: " + e.getMessage());
    }
  }
}
