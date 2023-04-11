package com.example.loan_project.controller;

import com.example.loan_project.dto.ResponseDto;
import com.example.loan_project.dto.TermsDto.Request;
import com.example.loan_project.dto.TermsDto.Response;
import com.example.loan_project.service.TermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/terms")
public class TermsController extends AbstractController{
  private final TermsService termsService;

  @PostMapping
  public ResponseDto<Response> create(@RequestBody Request request){
    return ok(termsService.create(request));
  }

  @GetMapping
  public ResponseDto<List<Response>> getAll(){
    return ok(termsService.getAll());
  }
}
