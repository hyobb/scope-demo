package com.example.scopedemo.user.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.scopedemo.common.domain.BaseEntity;
import com.example.scopedemo.slide.domain.Slide;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false, length = 100)
  private String password;

  @ElementCollection(fetch = FetchType.LAZY)
  @Enumerated(EnumType.STRING)
  private Set<UserRole> roles;

  @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private List<Slide> slides = new ArrayList<>();

  @Builder
  public User(String email, String password, Set<UserRole> roles) {
    this.email = email;
    this.password = password;
    this.roles = roles;
  }

  public enum UserRole {
    USER, ADMIN
  }

}
