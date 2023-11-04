package com.example.scopedemo.slide.services;

import org.springframework.web.multipart.MultipartFile;

import com.example.scopedemo.common.exceptions.ServiceException;
import com.example.scopedemo.slide.domain.File;
import com.example.scopedemo.slide.dtos.SlideDto.UploadFileRes;

public interface FileService {
  UploadFileRes getFileData(MultipartFile file);

  void upload(MultipartFile file, String fileName) throws ServiceException;

  byte[] download(File file) throws ServiceException;
}
