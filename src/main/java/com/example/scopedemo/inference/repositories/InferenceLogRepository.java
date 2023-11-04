package com.example.scopedemo.inference.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.scopedemo.inference.domain.InferenceLog;

public interface InferenceLogRepository extends JpaRepository<InferenceLog, Long> {
  Page<InferenceLog> findByUserId(Long userId, Pageable pageable);
}
