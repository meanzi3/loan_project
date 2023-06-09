package com.example.loan_project.service;

import com.example.loan_project.domain.AcceptTerms;
import com.example.loan_project.domain.Application;
import com.example.loan_project.domain.Terms;
import com.example.loan_project.dto.ApplicationDto;
import com.example.loan_project.dto.ApplicationDto.Response;
import com.example.loan_project.dto.ApplicationDto.Request;
import com.example.loan_project.exception.BaseException;
import com.example.loan_project.exception.ResultType;
import com.example.loan_project.repository.AcceptTermsRepository;
import com.example.loan_project.repository.ApplicationRepository;
import com.example.loan_project.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService{

  private final ApplicationRepository applicationRepository;

  private final TermsRepository termsRepository;

  private final AcceptTermsRepository acceptTermsRepository;

  private final ModelMapper modelMapper;

  @Override
  public Response create(Request request) {
    Application application = modelMapper.map(request, Application.class);
    application.setAppliedAt(LocalDateTime.now());

    Application applied = applicationRepository.save(application);

    return modelMapper.map(applied, Response.class);
  }

  @Override
  public Response get(Long applicationId) {
    Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
      throw new BaseException((ResultType.SYSTEM_ERROR));
    });
    return modelMapper.map(application, Response.class);
  }

  @Override
  public Response update(Long applicationId, Request request) {
    Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    application.setName(request.getName());
    application.setCellPhone(request.getCellPhone());
    application.setEmail(request.getEmail());
    application.setHopeAmount(request.getHopeAmount());

    applicationRepository.save(application);

    return modelMapper.map(application, Response.class);
  }

  @Override
  public void delete(Long applicationId) {
    Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    application.setIsDeleted(true); // soft delete 방식

    applicationRepository.save(application);
  }

  @Override
  public Boolean acceptTerms(Long applicationId, ApplicationDto.AcceptTerms request) {
    applicationRepository.findById(applicationId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    List<Terms> termsList = termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId"));
    if(termsList.isEmpty()){
      throw new BaseException(ResultType.SYSTEM_ERROR);
    }

    List<Long> acceptTermsIds = request.getAcceptTermsIds();
    if(termsList.size() != acceptTermsIds.size()){
      throw new BaseException(ResultType.SYSTEM_ERROR);
    }

    List<Long> termsIds = termsList.stream().map(Terms::getTermsId).collect(Collectors.toList());
    Collections.sort(acceptTermsIds);

    if(!termsIds.containsAll(acceptTermsIds)){
      throw new BaseException(ResultType.SYSTEM_ERROR);
    }

    for(Long termsId : acceptTermsIds){
      AcceptTerms accepted = AcceptTerms.builder()
              .termsId(termsId)
              .applicationId(applicationId)
              .build();

      acceptTermsRepository.save(accepted);
    }

    return true;
  }
}
