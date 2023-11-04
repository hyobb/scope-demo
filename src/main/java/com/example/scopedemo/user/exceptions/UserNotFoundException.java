package com.example.scopedemo.user.exceptions;

import com.example.scopedemo.common.exceptions.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
  public UserNotFoundException(Long userId) {
    super(userId + " User is not found");
  }
}
