package com.example.loan_project.controller;

import com.example.loan_project.dto.ApplicationDto.Response;
import com.example.loan_project.dto.ApplicationDto.Request;
import com.example.loan_project.dto.ResponseDto;
import com.example.loan_project.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/applications")
public class ApplicationController extends AbstractController{

  private final ApplicationService applicationService;

  @PostMapping
  public ResponseDto<Response> create(@RequestBody Request request){
    return ok(applicationService.create(request));
  }
}
