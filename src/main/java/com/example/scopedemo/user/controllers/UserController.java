package com.example.scopedemo.user.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.scopedemo.auth.services.AuthService;
import com.example.scopedemo.common.dtos.ResponseDto;
import com.example.scopedemo.common.security.JwtFilter;
import com.example.scopedemo.user.dtos.AuthTokenDto;
import com.example.scopedemo.user.dtos.UserDto;
import com.example.scopedemo.user.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
  private final UserService userService;
  private final AuthService authService;

  @PostMapping("/sign-up")
  public ResponseDto<UserDto.Res> signUp(@Valid @RequestBody final UserDto.SignUpReq signUpReq) throws Exception {
    UserDto.Res res = new UserDto.Res(userService.signUp(signUpReq));

    return ResponseDto.of(res);
  }

  @PostMapping("/sign-in")
  public ResponseEntity<ResponseDto<AuthTokenDto.Res>> authenticate(
      @Valid @RequestBody final UserDto.SignInReq signInReq) {
    String jwt = authService.authorize(signInReq);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

    AuthTokenDto.Res res = new AuthTokenDto.Res(jwt);

    return ResponseEntity.ok().headers(httpHeaders).body(ResponseDto.of(res));
  }

}
