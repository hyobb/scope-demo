package com.example.scopedemo.inference.domain;

import com.example.scopedemo.common.domain.BaseEntity;
import com.example.scopedemo.slide.domain.Slide;
import com.example.scopedemo.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "inference_logs", indexes = {
    @Index(name = "idx_inference_logs_slide_id", columnList = "slide_id"),
    @Index(name = "idx_inference_logs_user_id", columnList = "user_id")
})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InferenceLog extends BaseEntity {
  @Enumerated(EnumType.STRING)
  @Column(insertable = false, updatable = false)
  private Type type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "slide_id", nullable = false, updatable = false)
  private Slide slide;

  public InferenceLog(User user, Slide slide, Type type) {
    this.user = user;
    this.slide = slide;
    this.type = type;
  }

  public enum Type {
    SUCCESS, FAIL
  }
}
