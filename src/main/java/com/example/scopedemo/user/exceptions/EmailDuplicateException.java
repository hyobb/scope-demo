package com.example.scopedemo.user.exceptions;

import com.example.scopedemo.common.exceptions.ErrorCode;
import com.example.scopedemo.common.exceptions.ValidationException;

public class EmailDuplicateException extends ValidationException {
  public EmailDuplicateException(String email) {
    super(ErrorCode.EMAIL_DUPLICATION, "Email: " + email + "is already in use.");
  }
}
