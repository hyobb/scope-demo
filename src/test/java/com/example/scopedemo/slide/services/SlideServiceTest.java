package com.example.scopedemo.slide.services;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.example.scopedemo.slide.domain.Slide;
import com.example.scopedemo.slide.repositories.SlideRepository;
import com.example.scopedemo.user.domain.User;
import com.example.scopedemo.user.repositories.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback
public class SlideServiceTest {
  @Autowired
  private SlideService slideService;

  @Autowired
  private SlideRepository slideRepository;

  @Autowired
  private UserRepository userRepository;

  private User user;
  private Slide slide;

  @BeforeEach
  public void setup() {
  }
}
