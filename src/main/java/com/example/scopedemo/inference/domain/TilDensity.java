package com.example.scopedemo.inference.domain;

import java.util.Random;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TilDensity {
  @Column(name = "min", nullable = false)
  private Float min;

  @Column(name = "avg", nullable = false)
  private Float avg;

  @Column(name = "max", nullable = false)
  private Float max;

  @Builder
  public TilDensity(final Float min, final Float avg, final Float max) {
    this.min = min;
    this.avg = avg;
    this.max = max;
  }

  public static TilDensity getDummTilDensity() {
    Random rand = new Random();
    return TilDensity.builder()
        .min(rand.nextFloat())
        .avg(rand.nextFloat())
        .max(rand.nextFloat())
        .build();
  }
}
