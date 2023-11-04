package com.example.scopedemo.inference.dtos;

import java.util.List;
import java.util.stream.Collectors;

import com.example.scopedemo.inference.domain.InferenceSuccessLog;
import com.example.scopedemo.inference.domain.TilDensity;

import lombok.Getter;

public class InferenceSuccessLogDto extends InferenceLogDto {
  @Getter
  public static class Res extends InferenceLogDto.Res {
    private boolean dicision;
    private Float score;
    private List<Grid> grids;

    public Res(InferenceSuccessLog inferenceSuccessLog) {
      super(inferenceSuccessLog);
      this.dicision = inferenceSuccessLog.isDicision();
      this.score = inferenceSuccessLog.getScore();
      this.grids = inferenceSuccessLog.getGrids().stream().map(Grid::new).collect(Collectors.toList());
    }

  }

  @Getter
  public static class Grid {
    private Long id;
    private TilDensity intratumoralTilDensity;
    private TilDensity stromalTilDensity;

    public Grid(com.example.scopedemo.inference.domain.Grid grid) {
      this.id = grid.getId();
      this.intratumoralTilDensity = grid.getIntratumoralTilDensity();
      this.stromalTilDensity = grid.getStromalTilDensity();
    }
  }
}
