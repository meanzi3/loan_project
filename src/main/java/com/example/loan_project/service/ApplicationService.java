package com.example.loan_project.service;

import com.example.loan_project.dto.ApplicationDto.AcceptTerms;
import com.example.loan_project.dto.ApplicationDto.Response;
import com.example.loan_project.dto.ApplicationDto.Request;

public interface ApplicationService {

  Response create(Request request);

  Response get(Long applicationId);

  Response update(Long applicationId, Request request);

  void delete(Long applicationId);

  Boolean acceptTerms(Long applicationId, AcceptTerms request);

  Response contract(Long applicationId);
}
