package com.example.scopedemo.slide.exceptions;

import com.example.scopedemo.common.exceptions.EntityNotFoundException;

public class SlideNotFoundException extends EntityNotFoundException {

  public SlideNotFoundException(Long slideId) {
    super(slideId + " Slide is not found");
  }
}
