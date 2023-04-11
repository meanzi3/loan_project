package com.example.loan_project.service;

import com.example.loan_project.dto.ApplicationDto.Response;
import com.example.loan_project.dto.ApplicationDto.Request;

public interface ApplicationService {

  Response create(Request request);

  Response get(Long applicationId);
}
