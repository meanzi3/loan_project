package com.example.loan_project.service;

import com.example.loan_project.dto.JudgmentDto.*;
import com.example.loan_project.dto.ApplicationDto.GrantAmount;

public interface JudgmentService {

  Response create(Request request);

  // 심사 Id로 조회
  Response get(Long judgmentId);

  // 신청 Id로 조회
  Response getJudgmentOfApplication(Long applicationId);

  // 수정
  Response update(Long judgmentId, Request request);

  // 삭제
  void delete(Long judgmentId);

  // 심사 금액 부여
  GrantAmount grant(Long judgmentId);
}
