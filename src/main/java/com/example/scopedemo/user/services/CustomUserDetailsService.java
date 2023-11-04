package com.example.scopedemo.user.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.scopedemo.user.domain.CustomUserDetails;
import com.example.scopedemo.user.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
  private final UserRepository userRepository;

  @Override
  @Transactional
  public CustomUserDetails loadUserByUsername(final String username) {
    logger.info("LoadUserByUserName: " + username);
    return new CustomUserDetails(userRepository.findOneByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException(username + " This user does not exist")));
  }
}