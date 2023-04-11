package com.example.loan_project.service;

import com.example.loan_project.dto.TermsDto.Request;
import com.example.loan_project.dto.TermsDto.Response;

public interface TermsService {

  Response create(Request request);
}
