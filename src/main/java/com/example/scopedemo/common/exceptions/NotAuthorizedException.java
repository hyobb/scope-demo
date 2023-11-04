package com.example.scopedemo.common.exceptions;

public class NotAuthorizedException extends ServiceException {
  public NotAuthorizedException() {
    super(ErrorCode.NOT_AUTHORIZED_ERROR);
  }
}
