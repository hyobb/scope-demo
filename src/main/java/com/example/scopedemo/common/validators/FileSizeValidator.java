package com.example.scopedemo.common.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import com.example.scopedemo.common.annotations.MaxFileSize;

public class FileSizeValidator implements ConstraintValidator<MaxFileSize, MultipartFile> {
  private double maxFileSize;

  @Override
  public void initialize(MaxFileSize constraintAnnotation) {
    this.maxFileSize = constraintAnnotation.value();
  }

  @Override
  public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
    return file == null || file.getSize() <= maxFileSize * 1024 * 1024;
  }
}