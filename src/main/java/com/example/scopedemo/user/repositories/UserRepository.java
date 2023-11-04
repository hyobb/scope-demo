package com.example.scopedemo.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.scopedemo.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findOneByEmail(String email);
}