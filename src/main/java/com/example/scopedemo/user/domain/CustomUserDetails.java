package com.example.scopedemo.user.domain;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.scopedemo.user.domain.User.UserRole;

import lombok.Getter;

@Getter
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
  private User user;

  public CustomUserDetails(User user) {
    super(String.valueOf(user.getId()), user.getPassword(), authorities(user.getRoles()));
    this.user = user;
  }

  private static Collection<? extends GrantedAuthority> authorities(Set<UserRole> roles) {
    return roles.stream()
        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
        .collect(Collectors.toSet());
  }
}
