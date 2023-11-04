package com.example.scopedemo.common.exceptions;

public class ValidationException extends ServiceException {

  public ValidationException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public ValidationException(String message) {
    super(ErrorCode.BAD_REQUEST_ERROR, message);
  }
}
