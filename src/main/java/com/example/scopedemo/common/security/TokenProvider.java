package com.example.scopedemo.common.security;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenProvider implements InitializingBean {
  private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
  private static final String AUTHORITIES_KEY = "auth";
  private final String secret;
  private final long tokenValidityInMilliseconds;
  private Key key;

  public TokenProvider() {
    this.secret = "a2FyaW10b2thcmltdG9rYXJpbXRva2FyaW10b2thcmltdG9rYXJpbXRva2FyaW10b2thcmltdG9rYXJpbXRva2FyaW10b2thcmltdG9rYXJpbXRva2FyaW10b2thcmltdG9rYXJpbXRva2FyaW10b2thcmltdG9rYXJpbXRva2FyaW10b2thcmltdG9rYXJpbQ==";
    this.tokenValidityInMilliseconds = 86400 * 1000;
  }

  @Override
  public void afterPropertiesSet() {
    byte[] keyBytes = Base64.getDecoder().decode(secret);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public String createToken(Authentication authentication) {
    String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

    long now = (new Date()).getTime();
    Date validity = new Date(now + this.tokenValidityInMilliseconds);

    return Jwts.builder()
        .setSubject(authentication.getName())
        .claim(AUTHORITIES_KEY, authorities)
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();
    // .setExpiration(validity)
  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts
        .parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();

    Collection<? extends GrantedAuthority> authorities = Arrays
        .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    UserDetails principal = new User(claims.getSubject(), "", authorities);

    logger.info("TokenProvider: " + principal);

    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  public void validateToken(String token) throws Exception {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      logger.error("잘못된 JWT 서명입니다.");
      throw e;
    } catch (ExpiredJwtException e) {
      logger.error("만료된 JWT 토큰입니다.");
      throw e;
    } catch (UnsupportedJwtException e) {
      logger.error("지원되지 않는 JWT 토큰입니다.");
      throw e;
    } catch (IllegalArgumentException e) {
      logger.error("JWT 토큰이 잘못되었습니다.");
      throw e;
    }
  }
}