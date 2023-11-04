package com.example.scopedemo.common.exceptions;

public class EntityNotFoundException extends ServiceException {

  public EntityNotFoundException(String message) {
    super(ErrorCode.ENTITY_NOT_FOUND, message);
  }
}
