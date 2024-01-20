package com.example.loan_project.service;

import com.example.loan_project.domain.Balance;
import com.example.loan_project.dto.BalanceDto;
import com.example.loan_project.dto.BalanceDto.Request;
import com.example.loan_project.dto.BalanceDto.UpdateRequest;
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
    Balance balance = modelMapper.map(request, Balance.class);

    BigDecimal entryAmount = request.getEntryAmount();
    balance.setApplicationId(applicationId);
    balance.setBalance(entryAmount);

    // request에 대한 값으로 먼저 세팅.
    // 삭제 했다가 다시 entry를 등록할 때, 잔고가 존재하는 경우 update 하도록
    balanceRepository.findByApplicationId(applicationId).ifPresent( b -> {
      balance.setBalanceId(b.getBalanceId());
      balance.setIsDeleted(b.getIsDeleted());
      balance.setCreatedAt(b.getCreatedAt());
      balance.setUpdatedAt(b.getUpdatedAt());
    });

    Balance saved = balanceRepository.save(balance);

    return modelMapper.map(saved, Response.class);
  }

  @Override
  public Response update(Long applicationId, UpdateRequest request) {
    // balance가 있는지 확인
    Balance balance = balanceRepository.findByApplicationId(applicationId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    BigDecimal beforeEntryAmount = request.getBeforeEntryAmount();
    BigDecimal afterEntryAmount = request.getAfterEntryAmount();
    BigDecimal updatedBalance = balance.getBalance();

    // as-is -> to-be
    updatedBalance = updatedBalance.subtract(beforeEntryAmount).add(afterEntryAmount);
    balance.setBalance(updatedBalance);

    Balance updated = balanceRepository.save(balance);

    return modelMapper.map(updated, Response.class);
  }
}
