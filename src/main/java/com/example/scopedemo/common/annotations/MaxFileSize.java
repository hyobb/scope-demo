package com.example.scopedemo.common.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import com.example.scopedemo.common.validators.FileSizeValidator;

@Documented
@Constraint(validatedBy = FileSizeValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxFileSize {
  String message() default "File size should be less than {value}MB";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  double value();
}