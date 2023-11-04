package com.example.scopedemo.common.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.example.scopedemo.common.exceptions.InvalidTokenException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
  private final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
  public static final String AUTHORIZATION_HEADER = "Authorization";
  private final TokenProvider tokenProvider;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws ServletException, java.io.IOException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    String jwt = resolveToken(httpServletRequest);

    try {
      tokenProvider.validateToken(jwt);
    } catch (Exception e) {
      throw new InvalidTokenException();
    }

    Authentication authentication = tokenProvider.getAuthentication(jwt);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(servletRequest, servletResponse);
  }

  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }

    return null;
  }
}