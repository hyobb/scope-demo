package com.example.scopedemo.common.exceptions;

public class InvalidTokenException extends ServiceException {
  public InvalidTokenException() {
    super(ErrorCode.INVALID_TOKEN_ERROR);
  }
}
