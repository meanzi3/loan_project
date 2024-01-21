package com.example.loan_project.service;

import com.example.loan_project.dto.BalanceDto.Request;
import com.example.loan_project.dto.BalanceDto.UpdateRequest;
import com.example.loan_project.dto.BalanceDto.RepaymentRequest;
import com.example.loan_project.dto.BalanceDto.Response;

public interface BalanceService {

  Response create(Long applicationId, Request request);

  Response get(Long applicationId);

  Response update(Long applicationId, UpdateRequest request);

  Response repaymentUpdate(Long applicationId, RepaymentRequest request);

  void delete(Long applicationId);
}
