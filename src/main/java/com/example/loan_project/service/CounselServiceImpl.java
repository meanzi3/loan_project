package com.example.loan_project.service;

import com.example.loan_project.domain.Counsel;
import com.example.loan_project.dto.CounselDto.Response;
import com.example.loan_project.dto.CounselDto.Request;
import com.example.loan_project.repository.CounselRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CounselServiceImpl implements CounselService{

  private final CounselRepository counselRepository;
  private final ModelMapper modelMapper;

  @Override
  public Response create(Request request){
    Counsel counsel = modelMapper.map(request, Counsel.class);
    counsel.setAppliedAt(LocalDateTime.now());

    Counsel created = counselRepository.save(counsel);

    return modelMapper.map(created, Response.class);
  }
}
