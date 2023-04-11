package com.example.loan_project.controller;

import com.example.loan_project.dto.ResponseDto;
import com.example.loan_project.dto.TermsDto.Request;
import com.example.loan_project.dto.TermsDto.Response;
import com.example.loan_project.service.TermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/terms")
public class TermsController extends AbstractController{
  private final TermsService termsService;

  @PostMapping
  public ResponseDto<Response> create(@RequestBody Request request){
    return ok(termsService.create(request));
  }
}
