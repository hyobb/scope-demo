package com.example.scopedemo.common.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.scopedemo.common.exceptions.ExceptionHandlerFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
  private final TokenProvider tokenProvider;
  private final ExceptionHandlerFilter exceptionHandlerFilter;

  @Override
  public void configure(HttpSecurity http) {
    http.addFilterBefore(
        new JwtFilter(tokenProvider),
        UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(exceptionHandlerFilter, JwtFilter.class);
  }
}