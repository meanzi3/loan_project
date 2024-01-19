package com.example.loan_project.service;

import com.example.loan_project.dto.EntryDto.Response;
import com.example.loan_project.dto.EntryDto.Request;


public interface EntryService {

  Response create(Long applicationId, Request request);

}
