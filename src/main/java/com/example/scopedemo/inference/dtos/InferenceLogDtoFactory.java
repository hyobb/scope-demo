package com.example.scopedemo.inference.dtos;

import com.example.scopedemo.inference.domain.InferenceFailLog;
import com.example.scopedemo.inference.domain.InferenceLog;
import com.example.scopedemo.inference.domain.InferenceSuccessLog;

public class InferenceLogDtoFactory {
  public static InferenceLogDto.Res createResDto(InferenceLog log) {
    switch (log.getType()) {
      case SUCCESS:
        return new InferenceSuccessLogDto.Res((InferenceSuccessLog) log);
      case FAIL:
        return new InferenceFailLogDto.Res((InferenceFailLog) log);
      default:
        throw new IllegalArgumentException("Unsupported log type: " + log.getType());
    }
  }
}