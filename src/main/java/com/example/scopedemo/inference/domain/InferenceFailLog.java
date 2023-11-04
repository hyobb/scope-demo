package com.example.scopedemo.inference.domain;

import com.example.scopedemo.slide.domain.Slide;
import com.example.scopedemo.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "inference_fail_logs")
@DiscriminatorValue("FAIL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InferenceFailLog extends InferenceLog {
  @Column(name = "error_message")
  private String errorMessage;

  @Builder
  public InferenceFailLog(User user, Slide slide, String errorMessage) {
    super(user, slide, InferenceLog.Type.FAIL);
    this.errorMessage = errorMessage;
  }
}
