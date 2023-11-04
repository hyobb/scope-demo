package com.example.scopedemo.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.scopedemo.common.exceptions.ExceptionHandlerFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
  private final static String[] WHITE_LIST = { "/users/sign-up", "/users/sign-in", "/file/**", "/springdoc/**" };

  @Autowired
  private final TokenProvider tokenProvider;

  @Autowired
  private final ExceptionHandlerFilter exceptionHandlerFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers(WHITE_LIST);
  }

  @Bean
  protected SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
    http.csrf((csrf) -> csrf.disable())
        .formLogin((formLogin) -> formLogin
            .permitAll())
        .sessionManagement((sessionManagement) -> sessionManagement
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
            .anyRequest()
            .authenticated())
        .apply(new JwtSecurityConfig(tokenProvider, exceptionHandlerFilter));

    return http.build();
  }
}