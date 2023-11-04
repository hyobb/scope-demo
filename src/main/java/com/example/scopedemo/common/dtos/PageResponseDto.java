package com.example.scopedemo.common.dtos;

import org.springframework.data.domain.Pageable;

import lombok.Getter;

@Getter
public class PageResponseDto<T> extends ResponseDto<T> {

  private final Pageable pageable;

  public PageResponseDto(T data, Pageable pageable) {
    super(data);
    this.pageable = pageable;
  }

  public static <T> PageResponseDto<T> of(T data, Pageable pageable) {
    return new PageResponseDto<>(data, pageable);
  }
}
