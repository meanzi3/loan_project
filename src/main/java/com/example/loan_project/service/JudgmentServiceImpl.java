package com.example.loan_project.service;

import com.example.loan_project.domain.Application;
import com.example.loan_project.domain.Judgment;
import com.example.loan_project.dto.ApplicationDto;
import com.example.loan_project.dto.JudgmentDto.*;
import com.example.loan_project.exception.BaseException;
import com.example.loan_project.exception.ResultType;
import com.example.loan_project.repository.ApplicationRepository;
import com.example.loan_project.repository.JudgmentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class JudgmentServiceImpl implements JudgmentService{

  private final JudgmentRepository judgmentRepository;

  private final ApplicationRepository applicationRepository;

  private final ModelMapper modelMapper;

  @Override
  public Response create(Request request) {
    // 신청 정보 검증
    Long applicationId = request.getApplicationId();
    if(!isPresentApplication(applicationId)){
      throw new BaseException(ResultType.SYSTEM_ERROR);
    }

    // request dto -> entity -> save
    Judgment judgment = modelMapper.map(request, Judgment.class);
    Judgment saved = judgmentRepository.save(judgment);

    // save -> response dto
    return modelMapper.map(saved, Response.class);
  }

  @Override
  public Response get(Long judgmentId) {
    Judgment judgment = judgmentRepository.findById(judgmentId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    return modelMapper.map(judgment, Response.class);
  }

  @Override
  public Response getJudgmentOfApplication(Long applicationId) {
    if(!isPresentApplication(applicationId)){
      throw new BaseException(ResultType.SYSTEM_ERROR);
    }

    Judgment judgment = judgmentRepository.findByApplicationId(applicationId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    return modelMapper.map(judgment, Response.class);
  }

  @Override
  public Response update(Long judgmentId, Request request) {
    Judgment judgment = judgmentRepository.findById(judgmentId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    judgment.setName(request.getName());
    judgment.setApprovalAmount(request.getApprovalAmount());

    judgmentRepository.save(judgment);

    return modelMapper.map(judgment, Response.class);
  }

  @Override
  public void delete(Long judgmentId) {
    Judgment judgment = judgmentRepository.findById(judgmentId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    judgment.setIsDeleted(true);

    // soft delete
    judgmentRepository.save(judgment);
  }

  @Override
  public ApplicationDto.GrantAmount grant(Long judgmentId) {
    Judgment judgment = judgmentRepository.findById(judgmentId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    Long applicationId = judgment.getApplicationId();
    Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    BigDecimal approvalAmount = judgment.getApprovalAmount();
    application.setApprovalAmount(approvalAmount);

    applicationRepository.save(application);

    return modelMapper.map(application, ApplicationDto.GrantAmount.class);
  }

  private boolean isPresentApplication(Long applicationId){
    return applicationRepository.findById(applicationId).isPresent();
  }
}
