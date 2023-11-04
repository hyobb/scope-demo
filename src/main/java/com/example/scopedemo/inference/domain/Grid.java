package com.example.scopedemo.inference.domain;

import com.example.scopedemo.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "grids", indexes = {
    @Index(name = "idx_grids_inference_success_log_id", columnList = "inference_success_log_id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Grid extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "inference_success_log_id", nullable = false, updatable = false)
  @JsonBackReference
  private InferenceSuccessLog inferenceSuccessLog;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "min", column = @Column(name = "intratumoral_min")),
      @AttributeOverride(name = "avg", column = @Column(name = "intratumoral_avg")),
      @AttributeOverride(name = "max", column = @Column(name = "intratumoral_max"))
  })
  private TilDensity intratumoralTilDensity;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "min", column = @Column(name = "stromal_min")),
      @AttributeOverride(name = "avg", column = @Column(name = "stromal_avg")),
      @AttributeOverride(name = "max", column = @Column(name = "stromal_max"))
  })
  private TilDensity stromalTilDensity;

  @Builder
  public Grid(InferenceSuccessLog inferenceSuccessLog, TilDensity intratumoralTilDensity,
      TilDensity stromalTilDensity) {
    this.inferenceSuccessLog = inferenceSuccessLog;
    this.intratumoralTilDensity = intratumoralTilDensity;
    this.stromalTilDensity = stromalTilDensity;
  }

  public static Grid getDummyGrid(InferenceSuccessLog inferenceSuccessLog) {
    return Grid.builder()
        .inferenceSuccessLog(inferenceSuccessLog)
        .intratumoralTilDensity(TilDensity.getDummTilDensity())
        .stromalTilDensity(TilDensity.getDummTilDensity())
        .build();
  }
}
