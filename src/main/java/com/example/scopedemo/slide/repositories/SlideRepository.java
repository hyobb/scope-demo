package com.example.scopedemo.slide.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.scopedemo.slide.domain.Slide;

public interface SlideRepository extends JpaRepository<Slide, Long> {
  Page<Slide> findByUserId(Long userId, Pageable pageable);

  Page<Slide> findByUserIdAndFileOriginalNameContainingIgnoreCase(Long userId, String fileName, Pageable pageable);

}