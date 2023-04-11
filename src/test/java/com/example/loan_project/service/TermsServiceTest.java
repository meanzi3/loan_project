package com.example.loan_project.service;

import com.example.loan_project.domain.Terms;
import com.example.loan_project.dto.TermsDto.Request;
import com.example.loan_project.dto.TermsDto.Response;
import com.example.loan_project.repository.TermsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TermsServiceTest {

  @InjectMocks
  TermsServiceImpl termsService;

  @Mock
  private TermsRepository termsRepository;

  @Spy
  private ModelMapper modelMapper;

  @Test
  void Should_ReturnResponseOfNewTermsEntity_When_RequestTerms(){
    Terms entity = Terms.builder()
                    .name("대출 이용 약관")
                    .termsDetailUrl("https://asdf.asdf.asdgf.x/sdfw")
                    .build();

    Request request = Request.builder()
            .name("대출 이용 약관")
            .termsDetailUrl("https://asdf.asdf.asdgf.x/sdfw")
            .build();

    when(termsRepository.save(ArgumentMatchers.any(Terms.class))).thenReturn(entity);

     Response actual = termsService.create(request);

     assertThat(actual.getName()).isSameAs(entity.getName());
     assertThat(actual.getTermsDetailUrl()).isSameAs(entity.getTermsDetailUrl());
  }

  @Test
  void Should_ReturnAllResponseOfExistTermsEntities_When_RequestTermsList(){
    Terms entityA = Terms.builder()
            .name("대출 이용 약관1")
            .termsDetailUrl("https://asdf/dfgxcdfsd.com")
            .build();

    Terms entityB = Terms.builder()
            .name("대출 이용 약관2")
            .termsDetailUrl("https://asdf/dfgxcdfsd.com")
            .build();

    List<Terms> list = new ArrayList<>(Arrays.asList(entityA, entityB));

    when(termsRepository.findAll()).thenReturn(list);

    List<Response> actual = termsService.getAll();

    assertThat(actual.size()).isSameAs(list.size());
  }
}
