package com.example.scopedemo.user.dtos;

import lombok.Getter;

public class AuthTokenDto {
  @Getter
  public static class Res {
    private String token;

    public Res(String token) {
      this.token = token;
    }
  }
}
