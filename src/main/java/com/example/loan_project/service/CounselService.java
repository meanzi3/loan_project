package com.example.loan_project.service;

import com.example.loan_project.dto.CounselDto.Response;
import com.example.loan_project.dto.CounselDto.Request;

public interface CounselService {

  Response create(Request request);
}