package com.example.loan_project.service;

import com.example.loan_project.domain.Application;
import com.example.loan_project.domain.Entry;
import com.example.loan_project.domain.Repayment;
import com.example.loan_project.dto.BalanceDto;
import com.example.loan_project.dto.RepaymentDto.Response;
import com.example.loan_project.dto.RepaymentDto.ListResponse;
import com.example.loan_project.dto.RepaymentDto.UpdateResponse;
import com.example.loan_project.dto.RepaymentDto.Request;
import com.example.loan_project.exception.BaseException;
import com.example.loan_project.exception.ResultType;
import com.example.loan_project.repository.ApplicationRepository;
import com.example.loan_project.repository.EntryRepository;
import com.example.loan_project.repository.RepaymentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepaymentServiceImpl implements RepaymentService{

  private final RepaymentRepository repaymentRepository;

  private final ApplicationRepository applicationRepository;

  private final EntryRepository entryRepository;
  
  private final BalanceService balanceService;

  private final ModelMapper modelMapper;

  @Override
  public Response create(Long applicationId, Request request) {

    // validation
    // 1. 계약을 완료한 신청 정보
    // 2. 집행이 되어 있는 신청 정보
    if(!isRepayableApplication(applicationId)){
      throw new BaseException(ResultType.SYSTEM_ERROR);
    }

    Repayment repayment = modelMapper.map(request, Repayment.class);
    repayment.setApplicationId(applicationId);

    repaymentRepository.save(repayment);

    // 잔고
    BalanceDto.Response updatedBalance = balanceService.repaymentUpdate(applicationId,
            BalanceDto.RepaymentRequest.builder()
                    .repaymentAmount(request.getRepaymentAmount())
                    .type(BalanceDto.RepaymentRequest.RepaymentType.REMOVE)
                    .build());

    Response response = modelMapper.map(repayment, Response.class);
    response.setBalance(updatedBalance.getBalance());

    return response;
  }

  @Override
  public List<ListResponse> get(Long applicationId) {
    List<Repayment> repayments = repaymentRepository.findAllByApplicationId(applicationId);

    return repayments.stream().map(r -> modelMapper.map(r, ListResponse.class)).collect(Collectors.toList());
  }

  @Override
  public UpdateResponse update(Long repaymentId, Request request) {
    Repayment repayment = repaymentRepository.findById(repaymentId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    Long applicationId = repayment.getApplicationId();
    BigDecimal beforeRepaymentAmount = repayment.getRepaymentAmount();

    // 500 - 100(상환금) = 400
    // 400 + 100(상환금) = 500 -> 다시 이전에 상환한 금액을 더하고
    balanceService.repaymentUpdate(applicationId,
            BalanceDto.RepaymentRequest.builder()
                    .repaymentAmount(beforeRepaymentAmount)
                    .type(BalanceDto.RepaymentRequest.RepaymentType.ADD)
                    .build());

    // 500 - 300(수정한 상환금) = 200 -> 수정한 상환 금액을 빼준다.
    repayment.setRepaymentAmount(request.getRepaymentAmount());
    repaymentRepository.save(repayment);
    BalanceDto.Response updatedBalance = balanceService.repaymentUpdate(applicationId, BalanceDto.RepaymentRequest.builder()
            .repaymentAmount(request.getRepaymentAmount())
            .type(BalanceDto.RepaymentRequest.RepaymentType.REMOVE).build());

    // response
    return UpdateResponse.builder()
            .applicationId(applicationId)
            .beforeRepaymentAmount(beforeRepaymentAmount)
            .afterRepaymentAmount(request.getRepaymentAmount())
            .balance(updatedBalance.getBalance())
            .createdAt(repayment.getCreatedAt())
            .updatedAt(repayment.getUpdatedAt())
            .build();
  }

  @Override
  public void delete(Long repaymentId) {
    Repayment repayment = repaymentRepository.findById(repaymentId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    Long applicationId = repayment.getApplicationId();
    BigDecimal removeRepaymentAmount = repayment.getRepaymentAmount();

    // 상환금을 다시 더해줌
    balanceService.repaymentUpdate(applicationId,
            BalanceDto.RepaymentRequest.builder()
                    .repaymentAmount(removeRepaymentAmount)
                    .type(BalanceDto.RepaymentRequest.RepaymentType.ADD)
                    .build());

    // soft delete
    repayment.setIsDeleted(true);
    repaymentRepository.save(repayment);
  }

  private boolean isRepayableApplication(Long applicationId){
    Optional<Application> existedApplication = applicationRepository.findById(applicationId);
    if(existedApplication.isEmpty()){
      return false;
    }

    if(existedApplication.get().getContractedAt() == null){
      return false;
    }

    Optional<Entry> existedEntry = entryRepository.findByApplicationId(applicationId);
    return existedEntry.isPresent();

  }
}
