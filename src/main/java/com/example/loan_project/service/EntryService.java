package com.example.loan_project.service;

import com.example.loan_project.dto.EntryDto;
import com.example.loan_project.dto.EntryDto.Response;
import com.example.loan_project.dto.EntryDto.Request;
import com.example.loan_project.dto.EntryDto.UpdateResponse;


public interface EntryService {

  Response create(Long applicationId, Request request);

  Response get(Long applicationId);

  UpdateResponse update(Long entryId, Request request);
}
