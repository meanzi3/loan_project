package com.example.loan_project.controller;

import com.example.loan_project.dto.CounselDto;
import com.example.loan_project.dto.CounselDto.Response;
import com.example.loan_project.dto.CounselDto.Request;
import com.example.loan_project.dto.ResponseDto;
import com.example.loan_project.service.CounselService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/counsels")
public class CounselController extends AbstractController{

  private final CounselService counselService;

  @PostMapping
  public ResponseDto<Response>  create(@RequestBody Request request){
    return ok(counselService.create(request));
  }
}
