package com.example.scopedemo.slide.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.scopedemo.common.domain.BaseEntity;
import com.example.scopedemo.inference.domain.InferenceLog;
import com.example.scopedemo.user.domain.User;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "slides", indexes = {
    @Index(name = "idx_slides_user_id", columnList = "user_id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Slide extends BaseEntity {
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "name", column = @Column(name = "file_name")),
      @AttributeOverride(name = "originalName", column = @Column(name = "file_original_name")),
      @AttributeOverride(name = "url", column = @Column(name = "file_url")),
      @AttributeOverride(name = "size", column = @Column(name = "file_size"))
  })
  private File file;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @OneToMany(mappedBy = "slide", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private List<InferenceLog> inferenceLogs = new ArrayList<>();

  @Builder
  public Slide(final User user, final File file) {
    this.user = user;
    this.file = file;
  }
}
