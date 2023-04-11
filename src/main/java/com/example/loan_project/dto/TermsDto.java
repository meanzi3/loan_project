package com.example.loan_project.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TermsDto implements Serializable {
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @Getter
  @Setter
  public static class Request{
    private String name;
    private String termsDetailUrl;
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @Getter
  @Setter
  public static class Response{
    private Long termsId;
    private String name;
    private String termsDetailUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }
}
