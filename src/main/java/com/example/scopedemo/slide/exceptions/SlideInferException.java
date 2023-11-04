package com.example.scopedemo.slide.exceptions;

import com.example.scopedemo.common.exceptions.ErrorCode;
import com.example.scopedemo.common.exceptions.ServiceException;

public class SlideInferException extends ServiceException {

  public SlideInferException(String message) {
    super(ErrorCode.SLIDE_INFER_FAILED, message);
  }

}
