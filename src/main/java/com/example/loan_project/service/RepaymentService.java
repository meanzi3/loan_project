package com.example.loan_project.service;

import com.example.loan_project.dto.RepaymentDto.Request;
import com.example.loan_project.dto.RepaymentDto.Response;

public interface RepaymentService {

  Response create(Long applicationId, Request request);
}
