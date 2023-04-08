package com.example.loan_project.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ApplicationDto implements Serializable {

  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @Getter
  @Setter
  public static class Request{
    private String name;

    private String cellPhone;

    private String email;

    private BigDecimal hopeAmount;
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @Getter
  @Setter
  public static class Response {
    private long applicationId;

    private String name;

    private String cellPhone;

    private String email;

    private BigDecimal hopeAmount;

    private LocalDateTime appliedAt;

    private LocalDateTime createAt;

    private LocalDateTime updatedAt;
  }
}