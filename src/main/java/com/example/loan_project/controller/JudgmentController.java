package com.example.loan_project.controller;

import com.example.loan_project.dto.JudgmentDto.*;
import com.example.loan_project.dto.ResponseDto;
import com.example.loan_project.service.JudgmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/judgments")
public class JudgmentController extends AbstractController{

  private final JudgmentService judgmentService;

  @PostMapping
  public ResponseDto<Response> create(@RequestBody Request request){
    return ok(judgmentService.create(request));
  }
}
