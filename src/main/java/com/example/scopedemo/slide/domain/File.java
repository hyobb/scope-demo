package com.example.scopedemo.slide.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {
  @Column(name = "name")
  private String name;

  @Column(name = "originalName")
  private String originalName;

  @Column(name = "url")
  private String url;

  @Column(name = "size")
  private long size;

  @Builder
  public File(final String name, final String originalName, final String url, final long size) {
    this.name = name;
    this.originalName = originalName;
    this.url = url;
    this.size = size;
  }
}
