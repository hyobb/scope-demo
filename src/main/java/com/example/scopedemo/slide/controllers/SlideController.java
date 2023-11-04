package com.example.scopedemo.slide.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.scopedemo.common.dtos.PageRequest;
import com.example.scopedemo.common.dtos.PageResponseDto;
import com.example.scopedemo.common.dtos.ResponseDto;
import com.example.scopedemo.common.security.SecurityUtil;
import com.example.scopedemo.inference.domain.InferenceLog;
import com.example.scopedemo.inference.dtos.InferenceLogDto;
import com.example.scopedemo.inference.dtos.InferenceLogDtoFactory;
import com.example.scopedemo.slide.domain.Slide;
import com.example.scopedemo.slide.dtos.SlideDto;
import com.example.scopedemo.slide.dtos.SlideDto.DownloadSlideFileDto;
import com.example.scopedemo.slide.services.SlideService;

import io.jsonwebtoken.io.IOException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SlideController {
  private final SlideService slideService;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/slides")
  public ResponseDto<SlideDto.Res> upload(
      @ModelAttribute final SlideDto.CreateSlideReq createSlideReq)
      throws IOException, java.io.IOException {
    Long currentUserId = SecurityUtil.getCurrentUserId();
    Slide slide = slideService.create(createSlideReq, currentUserId);
    SlideDto.Res res = new SlideDto.Res(slide);

    return ResponseDto.of(res);
  }

  @GetMapping("/users/{userId}/slides")
  public ResponseDto<List<SlideDto.Res>> getSlides(@PathVariable final Long userId,
      @Valid @RequestParam(name = "fileName", required = false) final String fileName,
      @Valid final PageRequest pageable)
      throws Exception {
    Long currentUserId = SecurityUtil.getCurrentUserId();
    Page<Slide> page = slideService.findAll(userId, currentUserId, fileName, pageable.of());
    List<SlideDto.Res> res = page.get().map(SlideDto.Res::new).collect(Collectors.toList());

    return PageResponseDto.of(res, page.getPageable());
  }

  @PostMapping("/slides/{slideId}/infer")
  public ResponseDto<InferenceLogDto.Res> infer(@PathVariable final Long slideId) throws Exception {
    Long currentUserId = SecurityUtil.getCurrentUserId();
    InferenceLog log = slideService.infer(slideId, currentUserId);
    InferenceLogDto.Res res = InferenceLogDtoFactory.createResDto(log);

    return ResponseDto.of(res);
  }

  @GetMapping("/slides/{slideId}/download")
  public ResponseEntity<ByteArrayResource> download(@PathVariable final Long slideId)
      throws UnsupportedEncodingException {
    Long currentUserId = SecurityUtil.getCurrentUserId();
    DownloadSlideFileDto dto = slideService.downloadSlideFile(slideId, currentUserId);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Content-type", "application/octet-stream");
    httpHeaders.add("Content-disposition",
        "attachment; filename=\"" + URLEncoder.encode(dto.getFileName(), "utf-8") + "\"");

    return ResponseEntity.ok().headers(httpHeaders).contentLength(dto.getFileData().length)
        .body(new ByteArrayResource(dto.getFileData()));
  }
}
