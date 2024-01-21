package com.example.loan_project.controller;

import com.example.loan_project.dto.EntryDto;
import com.example.loan_project.dto.EntryDto.Response;
import com.example.loan_project.dto.EntryDto.UpdateResponse;
import com.example.loan_project.dto.EntryDto.Request;
import com.example.loan_project.dto.RepaymentDto;
import com.example.loan_project.dto.ResponseDto;
import com.example.loan_project.service.EntryService;
import com.example.loan_project.service.RepaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/applications")
public class InternalController extends AbstractController{

  private final EntryService entryService;
  private final RepaymentService repaymentService;

  @PostMapping("{applicationId}/entries")
  public ResponseDto<Response> create(@PathVariable Long applicationId, @RequestBody Request request){
    return ok(entryService.create(applicationId, request));
  }

  @GetMapping("{applicationId}/entries")
  public ResponseDto<Response> get(@PathVariable Long applicationId){
    return ok(entryService.get(applicationId));
  }

  @PutMapping("/entries/{entryId}")
  public ResponseDto<UpdateResponse> update(@PathVariable Long entryId, @RequestBody Request request){
    return ok(entryService.update(entryId, request));
  }

  @DeleteMapping("/entries/{entryId}")
  public ResponseDto<Void> delete(@PathVariable Long entryId){
    entryService.delete(entryId);
    return ok();
  }

  @PostMapping("/{applicationId}/repayments")
  public ResponseDto<RepaymentDto.Response> create(@PathVariable Long applicationId, @RequestBody RepaymentDto.Request request){
    return ok(repaymentService.create(applicationId, request));
  }
}
