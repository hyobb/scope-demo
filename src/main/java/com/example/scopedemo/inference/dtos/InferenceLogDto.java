package com.example.scopedemo.inference.dtos;

import com.example.scopedemo.inference.domain.InferenceLog;

import lombok.Getter;

public class InferenceLogDto {
  @Getter
  public static class Res {
    private long id;

    private long userId;

    private long slideId;

    private String type;

    private String createdAt;

    public Res(InferenceLog inferenceLog) {
      this.id = inferenceLog.getId();
      this.userId = inferenceLog.getUser().getId();
      this.slideId = inferenceLog.getSlide().getId();
      this.type = inferenceLog.getType().toString();
      this.createdAt = inferenceLog.getCreatedAt().toString();
    }
  }
}
