package com.example.loan_project.service;

import com.example.loan_project.dto.RepaymentDto.Request;
import com.example.loan_project.dto.RepaymentDto.Response;
import com.example.loan_project.dto.RepaymentDto.ListResponse;
import com.example.loan_project.dto.RepaymentDto.UpdateResponse;

import java.util.List;

public interface RepaymentService {

  Response create(Long applicationId, Request request);

  List<ListResponse> get(Long applicationId);

  UpdateResponse update(Long repaymentId, Request request);
}
