package com.example.scopedemo.slide.dtos;

import org.springframework.web.multipart.MultipartFile;

import com.example.scopedemo.common.annotations.MaxFileSize;
import com.example.scopedemo.slide.domain.File;
import com.example.scopedemo.slide.domain.Slide;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SlideDto {
  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class GetSlidesReq {
    @NotBlank
    private String fileName;

    @Builder
    public GetSlidesReq(String fileName) {
      this.fileName = fileName;
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class CreateSlideReq {
    @MaxFileSize(value = 1024, message = "File size should be less than 1GB")
    private MultipartFile file;

    @Builder
    public CreateSlideReq(MultipartFile file) {
      this.file = file;
    }
  }

  @Getter
  public static class UploadFileRes {
    private String fileName;
    private String fileUrl;
    private long size;

    public UploadFileRes(String fileName, String fileUrl, long size) {
      this.fileName = fileName;
      this.fileUrl = fileUrl;
      this.size = size;
    }
  }

  @Getter
  public static class DownloadSlideFileDto {
    private byte[] fileData;
    private String fileName;

    @Builder
    public DownloadSlideFileDto(byte[] fileData, String fileName) {
      this.fileData = fileData;
      this.fileName = fileName;
    }
  }

  @Getter
  public static class Res {
    private long id;
    private long userId;
    private File file;

    public Res(Slide slide) {
      this.id = slide.getId();
      this.userId = slide.getUser().getId();
      this.file = slide.getFile();
    }
  }
}
