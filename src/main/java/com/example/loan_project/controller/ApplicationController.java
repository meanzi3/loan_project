package com.example.loan_project.controller;

import com.example.loan_project.dto.ApplicationDto.Response;
import com.example.loan_project.dto.ApplicationDto.Request;
import com.example.loan_project.dto.ResponseDto;
import com.example.loan_project.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/applications")
public class ApplicationController extends AbstractController{

  private final ApplicationService applicationService;

  @PostMapping
  public ResponseDto<Response> create(@RequestBody Request request){
    return ok(applicationService.create(request));
  }

  @GetMapping("/{applicationId}")
  public ResponseDto<Response> get(@PathVariable Long applicationId){
    return ok(applicationService.get(applicationId));
  }
}
