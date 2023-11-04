package com.example.scopedemo.user.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.scopedemo.user.domain.User;
import com.example.scopedemo.user.domain.User.UserRole;
import com.example.scopedemo.user.dtos.UserDto;
import com.example.scopedemo.user.exceptions.EmailDuplicateException;
import com.example.scopedemo.user.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  private final Logger logger = LoggerFactory.getLogger(UserService.class);
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public User signUp(final UserDto.SignUpReq signUpReq) throws Exception {
    final String email = signUpReq.getEmail();
    if (userRepository.findOneByEmail(email).orElse(null) != null) {
      logger.error("User registration failed. Email: {} is already in use.", email);
      throw new EmailDuplicateException(email);
    }

    User user = User.builder()
        .email(signUpReq.getEmail())
        .password(passwordEncoder.encode(signUpReq.getPassword()))
        .roles(Stream.of(UserRole.USER).collect(Collectors.toCollection((HashSet::new))))
        .build();
    return userRepository.save(user);
  }

  public Optional<User> findById(final Long userId) {
    return userRepository.findById(userId);
  }
}
