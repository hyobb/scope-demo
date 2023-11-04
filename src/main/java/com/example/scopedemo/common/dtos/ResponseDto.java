package com.example.scopedemo.common.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseDto<T> {

  private final T data;

  public static <T> ResponseDto<T> of(T data) {
    return new ResponseDto<>(data);
  }
}