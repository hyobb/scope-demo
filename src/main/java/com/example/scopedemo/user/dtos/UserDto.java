package com.example.scopedemo.user.dtos;

import com.example.scopedemo.user.domain.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {
  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class SignUpReq {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, and one number.")
    private String password;

    @Builder
    public SignUpReq(String email, String password) {
      this.email = email;
      this.password = password;
    }

    public User toEntity() {
      return User.builder()
          .email(this.email)
          .password(this.password)
          .build();
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class SignInReq {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, and one number.")
    private String password;

    @Builder
    public SignInReq(String email, String password) {
      this.email = email;
      this.password = password;
    }
  }

  @Getter
  public static class Res {
    private long id;
    private String email;

    public Res(User user) {
      this.id = user.getId();
      this.email = user.getEmail();
    }
  }
}
