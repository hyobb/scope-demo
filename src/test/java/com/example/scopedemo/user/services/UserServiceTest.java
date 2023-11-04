package com.example.scopedemo.user.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.example.scopedemo.user.domain.User;
import com.example.scopedemo.user.dtos.UserDto;
import com.example.scopedemo.user.exceptions.EmailDuplicateException;
import com.example.scopedemo.user.repositories.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback
public class UserServiceTest {
  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  private User user;

  @BeforeEach
  public void setup() {
    user = User.builder()
        .email("test@test.com")
        .password("Abcd1234")
        .build();
    userRepository.save(user);
  }

  @Test
  public void testFindById_UserFound() {
    Long userId = user.getId();
    User foundUser = userService.findById(userId).get();

    assertEquals(user, foundUser);
  }

  @Test
  public void testFindById_UserNotFound() {
    Long userId = user.getId() + 999;
    Optional<User> foundUser = userService.findById(userId);

    assertEquals(foundUser.isPresent(), false);
  }

  @Test
  public void testSignUp_Success() throws Exception {
    UserDto.SignUpReq req = UserDto.SignUpReq.builder()
        .email("test2@test.com")
        .password("Abcd1234").build();

    User user = userService.signUp(req);

    assertEquals(user.getEmail(), "test2@test.com");
  }

  @Test
  public void testSignUp_EmailAlreadyExists() {
    UserDto.SignUpReq req = UserDto.SignUpReq.builder()
        .email(user.getEmail())
        .password("Abcd1234").build();

    assertThrows(EmailDuplicateException.class, () -> userService.signUp(req));
  }
}
