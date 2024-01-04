package com.example.loan_project.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.loan_project.domain.Application;
import com.example.loan_project.domain.Judgment;
import com.example.loan_project.dto.JudgmentDto.*;
import com.example.loan_project.repository.ApplicationRepository;
import com.example.loan_project.repository.JudgmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JudgmentServiceTest {

  @InjectMocks
  private JudgmentServiceImpl judgmentService;

  @Mock
  private JudgmentRepository judgmentRepository;

  @Mock
  private ApplicationRepository applicationRepository;

  @Spy
  private ModelMapper modelMapper;

  @Test
  void Should_ReturnResponseOfNewJudgmentEntity_When_RequestNewJudgment(){

    Judgment judgment = Judgment.builder()
            .applicationId(1L)
            .name("Member Kim")
            .approvalAmount(BigDecimal.valueOf(50000000))
            .build();

    Request request = Request.builder()
            .applicationId(1L)
            .name("Member Kim")
            .approvalAmount(BigDecimal.valueOf(50000000))
            .build();

    // application find
    when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(Application.builder().build()));

    // judgment save
    when(judgmentRepository.save(ArgumentMatchers.any(Judgment.class))).thenReturn(judgment);

    Response actual = judgmentService.create(request);

    assertThat(actual.getName()).isSameAs(judgment.getName());
    assertThat(actual.getApplicationId()).isSameAs(judgment.getApplicationId());
    assertThat(actual.getApprovalAmount()).isSameAs(judgment.getApprovalAmount());

  }

}