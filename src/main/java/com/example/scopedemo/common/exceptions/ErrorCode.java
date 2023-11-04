package com.example.scopedemo.common.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_001", "Internal Server Error"),
  BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "COMMON_002", "Bad Request Error"),
  NOT_AUTHORIZED_ERROR(HttpStatus.UNAUTHORIZED, "COMMON_003", "Not Authorized Error"),
  ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON_004", "Entity Not found"),
  ACCESS_DENIED_ERROR(HttpStatus.FORBIDDEN, "COMMON_005", "Access Denied"),
  INVALID_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "COMMON_006", "Token is invalid"),

  EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "USER_001", "Email is already in use"),

  SLIDE_INFER_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "SLIDE_001", "Slide Inference Error");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  ErrorCode(HttpStatus httpStatus, String code, String message) {
    this.httpStatus = httpStatus;
    this.code = code;
    this.message = message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}