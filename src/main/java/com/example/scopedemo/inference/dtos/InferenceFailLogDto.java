package com.example.scopedemo.inference.dtos;

import com.example.scopedemo.inference.domain.InferenceFailLog;

import lombok.Getter;

public class InferenceFailLogDto extends InferenceLogDto {
  @Getter
  public static class Res extends InferenceLogDto.Res {
    private String errorMessage;

    public Res(InferenceFailLog inferenceFailLog) {
      super(inferenceFailLog);
      this.errorMessage = inferenceFailLog.getErrorMessage();
    }
  }
}
