package com.example.scopedemo.common.exceptions;

import java.nio.file.AccessDeniedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.scopedemo.common.dtos.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
  private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> ArgumentNotValidException(MethodArgumentNotValidException e) {
    logger.error("handleMethodArgumentNotValidException", e);
    final ErrorCode errorCode = ErrorCode.BAD_REQUEST_ERROR;
    final ErrorResponse response = ErrorResponse.of(errorCode, e.getBindingResult());
    return new ResponseEntity<>(response, errorCode.getHttpStatus());
  }

  @ExceptionHandler(AccessDeniedException.class)
  protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
    logger.error("handleAccessDeniedException", e);
    final ErrorCode errorCode = ErrorCode.ACCESS_DENIED_ERROR;
    final ErrorResponse response = ErrorResponse.of(errorCode, e.getMessage());
    return new ResponseEntity<>(response, errorCode.getHttpStatus());
  }

  @ExceptionHandler(ServiceException.class)
  protected ResponseEntity<ErrorResponse> handleBusinessException(final ServiceException e) {
    logger.error("handleServiceException", e);
    final ErrorCode errorCode = e.getErrorCode();
    final ErrorResponse response = ErrorResponse.of(errorCode, e.getMessage());
    return new ResponseEntity<>(response, errorCode.getHttpStatus());
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleException(Exception e) {
    logger.error("handleException", e);
    final ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    final ErrorResponse response = ErrorResponse.of(errorCode);
    return new ResponseEntity<>(response, errorCode.getHttpStatus());
  }
}
