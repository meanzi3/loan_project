package com.example.loan_project.service;

import com.example.loan_project.domain.Judgment;
import com.example.loan_project.dto.JudgmentDto.*;
import com.example.loan_project.exception.BaseException;
import com.example.loan_project.exception.ResultType;
import com.example.loan_project.repository.ApplicationRepository;
import com.example.loan_project.repository.JudgmentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

  private boolean isPresentApplication(Long applicationId){
    return applicationRepository.findById(applicationId).isPresent();
  }
}
