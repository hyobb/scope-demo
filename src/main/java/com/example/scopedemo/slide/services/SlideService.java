package com.example.scopedemo.slide.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.scopedemo.common.exceptions.AccessDeniedException;
import com.example.scopedemo.common.exceptions.ServiceException;
import com.example.scopedemo.inference.domain.Grid;
import com.example.scopedemo.inference.domain.InferenceFailLog;
import com.example.scopedemo.inference.domain.InferenceLog;
import com.example.scopedemo.inference.domain.InferenceSuccessLog;
import com.example.scopedemo.inference.repositories.InferenceLogRepository;
import com.example.scopedemo.slide.domain.File;
import com.example.scopedemo.slide.domain.Slide;
import com.example.scopedemo.slide.dtos.SlideDto;
import com.example.scopedemo.slide.dtos.SlideDto.DownloadSlideFileDto;
import com.example.scopedemo.slide.exceptions.SlideInferException;
import com.example.scopedemo.slide.exceptions.SlideNotFoundException;
import com.example.scopedemo.slide.repositories.SlideRepository;
import com.example.scopedemo.user.domain.User;
import com.example.scopedemo.user.exceptions.UserNotFoundException;
import com.example.scopedemo.user.services.UserService;

import io.jsonwebtoken.io.IOException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SlideService {
  private final Logger logger = LoggerFactory.getLogger(SlideService.class);
  private final FileService fileService;
  private final UserService userService;
  private final SlideRepository slideRepository;
  private final InferenceLogRepository inferenceLogRepository;

  public Page<Slide> findAll(final Long userId, final Long currentUserId, final String fileName,
      final Pageable pageable)
      throws ServiceException {
    if (!currentUserId.equals(userId)) {
      logger.error("Access failed. CurrentUser: {}", currentUserId);

      throw new AccessDeniedException("You are not authorized to view these slides");
    }

    if (StringUtils.hasLength(fileName)) {
      return slideRepository.findByUserIdAndFileOriginalNameContainingIgnoreCase(userId, fileName, pageable);
    } else {
      return slideRepository.findByUserId(userId, pageable);
    }
  }

  @Transactional
  public Slide create(final SlideDto.CreateSlideReq createSlideReq, Long currentUserId)
      throws IOException, java.io.IOException {

    User user = userService.findById(currentUserId).get();
    MultipartFile multipartFile = createSlideReq.getFile();
    SlideDto.UploadFileRes fileData = fileService.getFileData(multipartFile);
    File file = File.builder()
        .name(fileData.getFileName())
        .originalName(multipartFile.getOriginalFilename())
        .url(fileData.getFileUrl())
        .size(multipartFile.getSize())
        .build();
    Slide slide = Slide.builder()
        .user(user)
        .file(file)
        .build();

    fileService.upload(multipartFile, fileData.getFileName());

    return slideRepository.save(slide);
  }

  @Transactional
  public InferenceLog infer(final Long slideId, final Long currentUserId) throws ServiceException {
    User currentUser = userService.findById(currentUserId)
        .orElseThrow(() -> new UserNotFoundException(currentUserId));
    Slide slide = slideRepository.findById(slideId).orElseThrow(() -> new SlideNotFoundException(slideId));

    if (!currentUser.getId().equals(slide.getUser().getId())) {
      logger.error("Access failed. User: {}, Slide's User: {}", currentUserId, slide.getUser().getId());

      throw new AccessDeniedException("You are not authorized to view these slides");
    }

    try {
      if (slideId % 2 == 0) {
        throw new SlideInferException("Inference is failed with dummy message.");
      }

      InferenceSuccessLog inferenceSuccessLog = InferenceSuccessLog.builder()
          .user(currentUser)
          .slide(slide)
          .dicision(false)
          .score(0)
          .build();

      Grid dummyGrid1 = Grid.getDummyGrid(inferenceSuccessLog);
      Grid dummyGrid2 = Grid.getDummyGrid(inferenceSuccessLog);

      inferenceSuccessLog.addGrid(dummyGrid1);
      inferenceSuccessLog.addGrid(dummyGrid2);

      return inferenceLogRepository.save(inferenceSuccessLog);
    } catch (Exception e) {
      InferenceFailLog inferenceFailLog = InferenceFailLog.builder()
          .user(currentUser)
          .slide(slide)
          .errorMessage(e.getMessage()).build();

      return inferenceLogRepository.save(inferenceFailLog);
    }
  }

  public DownloadSlideFileDto downloadSlideFile(final Long slideId, final Long currentUserId) {
    Slide slide = slideRepository.findById(slideId).orElseThrow(() -> new SlideNotFoundException(slideId));
    if (!currentUserId.equals(slide.getUser().getId())) {
      logger.error("Access failed. CurrentUser: {}", currentUserId);

      throw new AccessDeniedException("You are not authorized to access this slide");
    }
    File file = slide.getFile();
    byte[] fileData = fileService.download(file);
    DownloadSlideFileDto dto = DownloadSlideFileDto.builder()
        .fileData(fileData)
        .fileName(file.getName()).build();

    return dto;
  }
}
