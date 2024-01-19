package com.example.loan_project.service;

import com.example.loan_project.dto.BalanceDto.Request;
import com.example.loan_project.dto.BalanceDto.Response;

public interface BalanceService {

  Response create(Long applicationId, Request request);
}
