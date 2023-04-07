package com.example.loan_project.service;

import com.example.loan_project.domain.Counsel;
import com.example.loan_project.dto.CounselDto.Response;
import com.example.loan_project.dto.CounselDto.Request;
import com.example.loan_project.exception.BaseException;
import com.example.loan_project.exception.ResultType;
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

  @Override
  public Response get(Long counselId) {
    Counsel counsel = counselRepository.findById(counselId).orElseThrow(()-> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    return modelMapper.map(counsel, Response.class);

  }

  @Override
  public Response update(Long counselId, Request request) {
    // id 로 조회
    Counsel counsel = counselRepository.findById(counselId).orElseThrow(() -> {
      throw new BaseException((ResultType.SYSTEM_ERROR));
    });

    // 수정
    counsel.setName(request.getName());
    counsel.setCellPhone(request.getCellPhone());
    counsel.setEmail(request.getEmail());
    counsel.setMemo(request.getMemo());
    counsel.setAddress(request.getAddress());
    counsel.setAddressDetail(request.getAddressDetail());
    counsel.setZipCode(request.getZipCode());

    // 저장
    counselRepository.save(counsel);

    return modelMapper.map(counsel, Response.class);
  }
}
