package com.example.scopedemo.common.dtos;

import org.springframework.data.domain.Sort;

public class PageRequest {
  private Integer page;
  private Integer size;
  private Sort.Direction direction;

  public void setPage(Integer page) {
    this.page = page;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public void setDirection(Sort.Direction direction) {
    this.direction = direction;
  }

  public org.springframework.data.domain.PageRequest of() {
    int DEFAULT_PAGE = 0;
    int DEFAULT_SIZE = 10;
    Sort.Direction DEFAULT_DIRECTION = Sort.Direction.ASC;

    int pageNum = page != null ? page : DEFAULT_PAGE;
    int pageSize = size != null ? Math.min(size, 50) : DEFAULT_SIZE;
    Sort.Direction sortDirection = direction != null ? direction : DEFAULT_DIRECTION;

    return org.springframework.data.domain.PageRequest.of(pageNum, pageSize, sortDirection, "id");
  }
}