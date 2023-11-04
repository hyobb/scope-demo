package com.example.scopedemo.common.exceptions;

public class AccessDeniedException extends ServiceException {

  public AccessDeniedException(String message) {
    super(ErrorCode.ACCESS_DENIED_ERROR, message);
  }

}
