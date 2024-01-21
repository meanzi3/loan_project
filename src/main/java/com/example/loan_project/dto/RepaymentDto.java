package com.example.loan_project.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RepaymentDto {

  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @Getter
  @Setter
  public static class Request{
    private BigDecimal repaymentAmount;
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @Getter
  @Setter
  public static class Response{
    private Long applicationId;
    private BigDecimal repaymentAmount;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }
}