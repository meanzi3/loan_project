package com.example.loan_project.service;

import com.example.loan_project.domain.Application;
import com.example.loan_project.domain.Entry;
import com.example.loan_project.dto.BalanceDto;
import com.example.loan_project.dto.EntryDto;
import com.example.loan_project.dto.EntryDto.Request;
import com.example.loan_project.dto.EntryDto.Response;
import com.example.loan_project.dto.EntryDto.UpdateResponse;
import com.example.loan_project.exception.BaseException;
import com.example.loan_project.exception.ResultType;
import com.example.loan_project.repository.ApplicationRepository;
import com.example.loan_project.repository.EntryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService{

  private final BalanceService balanceService;
  private final EntryRepository entryRepository;
  private final ApplicationRepository applicationRepository;
  private final ModelMapper modelMapper;

  @Override
  public Response create(Long applicationId, Request request) {

    // 계약 체결 여부 확인
    if(!isContractedApplication(applicationId)){
      throw new BaseException(ResultType.SYSTEM_ERROR);
    }

    Entry entry = modelMapper.map(request, Entry.class);
    entry.setApplicationId(applicationId);

    entryRepository.save(entry);

    // 대출 잔고 관리
    balanceService.create(applicationId,
            BalanceDto.Request.builder()
                    .entryAmount(request.getEntryAmount())
                    .build());

    return modelMapper.map(entry, Response.class);
  }

  @Override
  public Response get(Long applicationId) {
    Optional<Entry> entry = entryRepository.findByApplicationId(applicationId);

    if(entry.isPresent()){
      return modelMapper.map(entry, Response.class);
    } else{
      return null;
    }
  }

  @Override
  public UpdateResponse update(Long entryId, Request request) {
    // entry 존재 유무 확인
    Entry entry = entryRepository.findById(entryId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    // before -> after
    BigDecimal beforeEntryAmount = entry.getEntryAmount();
    entry.setEntryAmount(request.getEntryAmount());

    entryRepository.save(entry);

    // balance update
    Long applicationId = entry.getApplicationId();
    balanceService.update(applicationId,
            BalanceDto.UpdateRequest.builder()
                    .beforeEntryAmount(beforeEntryAmount)
                    .afterEntryAmount(request.getEntryAmount())
                    .build());

    // response
    return UpdateResponse.builder()
            .entryId(entryId)
            .applicationId(applicationId)
            .beforeEntryAmount(beforeEntryAmount)
            .afterEntryAmount(request.getEntryAmount())
            .build();
  }

  @Override
  public void delete(Long entryId) {
    Entry entry = entryRepository.findById(entryId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    // soft delete
    entry.setIsDeleted(true);

    entryRepository.save(entry);

    BigDecimal beforeEntryAmount = entry.getEntryAmount();

    // 대출 잔고를 0으로
    Long applicationId = entry.getApplicationId();
    balanceService.update(applicationId,
            BalanceDto.UpdateRequest.builder()
                    .beforeEntryAmount(beforeEntryAmount)
                    .afterEntryAmount(BigDecimal.ZERO)
                    .build());
  }

  private boolean isContractedApplication(Long applicationId){
    Optional<Application> existed = applicationRepository.findById(applicationId);
    if(existed.isEmpty()){
      return false;
    }

    return existed.get().getContractedAt() != null;
  }
}
