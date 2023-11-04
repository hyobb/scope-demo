package com.example.scopedemo.inference.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.scopedemo.common.dtos.PageRequest;
import com.example.scopedemo.common.dtos.PageResponseDto;
import com.example.scopedemo.common.dtos.ResponseDto;
import com.example.scopedemo.common.security.SecurityUtil;
import com.example.scopedemo.inference.domain.InferenceLog;
import com.example.scopedemo.inference.dtos.InferenceLogDto;
import com.example.scopedemo.inference.dtos.InferenceLogDtoFactory;
import com.example.scopedemo.inference.services.InferenceLogService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InferenceLogController {
  private final InferenceLogService inferencelogService;

  @GetMapping("/users/{userId}/inference-logs")
  public ResponseDto<List<InferenceLogDto.Res>> getInferenceLogs(@PathVariable final Long userId,
      @Valid final PageRequest pageable) throws Exception {
    Long currentUserId = SecurityUtil.getCurrentUserId();
    Page<InferenceLog> page = inferencelogService.findAll(userId, currentUserId, pageable.of());
    List<InferenceLogDto.Res> res = page.get().map(InferenceLogDtoFactory::createResDto).collect(Collectors.toList());

    return PageResponseDto.of(res, page.getPageable());
  }
}
