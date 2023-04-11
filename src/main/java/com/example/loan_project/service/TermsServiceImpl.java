package com.example.loan_project.service;

import com.example.loan_project.domain.Terms;
import com.example.loan_project.dto.TermsDto;
import com.example.loan_project.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TermsServiceImpl implements TermsService {

  private final TermsRepository termsRepository;

  private final ModelMapper modelMapper;


  @Override
  public TermsDto.Response create(TermsDto.Request request) {
    Terms terms = modelMapper.map(request, Terms.class);
    Terms created = termsRepository.save(terms);

    return modelMapper.map(created, TermsDto.Response.class);
  }
}
