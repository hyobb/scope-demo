
package com.example.scopedemo.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.scopedemo.common.exceptions.NotAuthorizedException;

public class SecurityUtil {
  private SecurityUtil() {
  }

  public static Long getCurrentUserId() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getName() == null) {
      throw new NotAuthorizedException();
    }

    return Long.parseLong(authentication.getName());
  }
}
