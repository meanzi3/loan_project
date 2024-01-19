package com.example.loan_project.service;

import com.example.loan_project.domain.Balance;
import com.example.loan_project.dto.BalanceDto.Request;
import com.example.loan_project.dto.BalanceDto.Response;
import com.example.loan_project.exception.BaseException;
import com.example.loan_project.exception.ResultType;
import com.example.loan_project.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService{

  private final BalanceRepository balanceRepository;
  private final ModelMapper modelMapper;

  @Override
  public Response create(Long applicationId, Request request) {

    // 이미 집행된 잔고가 있는지 확인
    if(balanceRepository.findByApplicationId(applicationId).isPresent()){
      throw new BaseException(ResultType.SYSTEM_ERROR);
    }
    Balance balance = modelMapper.map(request, Balance.class);

    BigDecimal entryAmount = request.getEntryAmount();
    balance.setApplicationId(applicationId);
    balance.setBalance(entryAmount);

    Balance saved = balanceRepository.save(balance);

    return modelMapper.map(saved, Response.class);
  }
}
