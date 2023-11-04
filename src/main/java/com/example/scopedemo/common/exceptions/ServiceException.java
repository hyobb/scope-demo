package com.example.scopedemo.common.exceptions;

public class ServiceException extends RuntimeException {

  private ErrorCode errorCode;

  public ServiceException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public ServiceException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}