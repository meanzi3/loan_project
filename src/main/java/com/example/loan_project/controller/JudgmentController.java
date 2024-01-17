package com.example.loan_project.controller;

import com.example.loan_project.dto.ApplicationDto;
import com.example.loan_project.dto.JudgmentDto.*;
import com.example.loan_project.dto.ResponseDto;
import com.example.loan_project.service.JudgmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/judgments")
public class JudgmentController extends AbstractController{

  private final JudgmentService judgmentService;

  @PostMapping
  public ResponseDto<Response> create(@RequestBody Request request){
    return ok(judgmentService.create(request));
  }

  @GetMapping("/{judgmentId}")
  public ResponseDto<Response> get(@PathVariable Long judgmentId){
    return ok(judgmentService.get(judgmentId));
  }

  @GetMapping("/applications/{applicationId}")
  public ResponseDto<Response> getJudgmentOfApplication(@PathVariable Long applicationId){
    return ok(judgmentService.getJudgmentOfApplication(applicationId));
  }

  @PutMapping("/{judgmentId}")
  public ResponseDto<Response> update(@PathVariable Long judgmentId, @RequestBody Request request){
    return ok(judgmentService.update(judgmentId, request));
  }

  @DeleteMapping("/{judgmentId}")
  public ResponseDto<Void> delete(@PathVariable Long judgmentId){
    judgmentService.delete(judgmentId);
    return ok();
  }

  @PatchMapping("/{judgmentId}/grants")
  public ResponseDto<ApplicationDto.GrantAmount> grant(@PathVariable Long judgmentId){
    return ok(judgmentService.grant(judgmentId));
  }
}
