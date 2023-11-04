package com.example.scopedemo.inference.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;

import com.example.scopedemo.slide.domain.Slide;
import com.example.scopedemo.user.domain.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "inference_success_logs")
@DiscriminatorValue("SUCCESS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InferenceSuccessLog extends InferenceLog {
  @Column(name = "decision", nullable = false)
  private boolean dicision;

  @Min(0)
  @Max(1)
  @Column(name = "score", nullable = false)
  private Float score;

  @OneToMany(mappedBy = "inferenceSuccessLog", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  @JsonManagedReference
  @BatchSize(size = 50)
  private List<Grid> grids = new ArrayList<>();

  @Builder
  public InferenceSuccessLog(User user, Slide slide, boolean dicision, float score) {
    super(user, slide, InferenceLog.Type.SUCCESS);
    this.dicision = dicision;
    this.score = score;
  }

  @Transactional
  public void addGrid(Grid grid) {
    this.grids.add(grid);
  }
}
