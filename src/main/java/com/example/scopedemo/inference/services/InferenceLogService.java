package com.example.scopedemo.inference.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.scopedemo.common.exceptions.AccessDeniedException;
import com.example.scopedemo.inference.domain.InferenceLog;
import com.example.scopedemo.inference.repositories.InferenceLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InferenceLogService {
  private final Logger logger = LoggerFactory.getLogger(InferenceLogService.class);

  private final InferenceLogRepository inferenceLogRepository;

  public Page<InferenceLog> findAll(final Long userId, final Long currentUserId, final Pageable pageable)
      throws Exception {
    if (!userId.equals(currentUserId)) {
      logger.error("Access failed. CurrentUser: {}", currentUserId);
      throw new AccessDeniedException("You are not authorized to view these inference logs");
    }

    return inferenceLogRepository.findByUserId(userId, pageable);
  }
}
