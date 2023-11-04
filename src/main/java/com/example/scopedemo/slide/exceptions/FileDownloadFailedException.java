package com.example.scopedemo.slide.exceptions;

import com.example.scopedemo.common.exceptions.ErrorCode;
import com.example.scopedemo.common.exceptions.ServiceException;

public class FileDownloadFailedException extends ServiceException {

  public FileDownloadFailedException(String message) {
    super(ErrorCode.INTERNAL_SERVER_ERROR, message);
  }
}
