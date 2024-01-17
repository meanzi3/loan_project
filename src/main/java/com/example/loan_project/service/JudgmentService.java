package com.example.loan_project.service;

import com.example.loan_project.dto.JudgmentDto.*;

public interface JudgmentService {

  Response create(Request request);

  // 심사 Id로 조회
  Response get(Long judgmentId);

  // 신청 Id로 조회
  Response getJudgmentOfApplication(Long applicationId);

  // 수정
  Response update(Long judgmentId, Request request);

}
