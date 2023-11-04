package com.example.scopedemo.common.exceptions;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.scopedemo.common.dtos.ErrorResponse;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    try {
      filterChain.doFilter(request, response);
    } catch (InvalidTokenException ex) {
      // throw ex;
      setErrorResponse(ex.getErrorCode(), request, response, ex);
    }
  }

  public void setErrorResponse(ErrorCode errorCode, HttpServletRequest request,
      HttpServletResponse response, Throwable ex) throws IOException {

    response.setStatus(errorCode.getHttpStatus().value());
    response.setContentType("application/json; charset=UTF-8");
    response.getWriter().write(
        ErrorResponse.of(errorCode, ex.getMessage()).convertToJson());
  }
}