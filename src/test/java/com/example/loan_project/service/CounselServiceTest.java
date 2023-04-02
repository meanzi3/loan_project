package com.example.loan_project.service;

import com.example.loan_project.domain.Counsel;
import com.example.loan_project.dto.CounselDto.Request;
import com.example.loan_project.dto.CounselDto.Response;
import com.example.loan_project.repository.CounselRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CounselServiceTest {
  @InjectMocks
  CounselServiceImpl counselService;

  @Mock
  private CounselRepository counselRepository;

  @Spy
  private ModelMapper modelMapper;

  @Test
  void Should_ReturnResponseOfNewCounselEntity_When_RequestCounsel(){
    // given
    Counsel entity = Counsel.builder()
            .name("Member Kim")
            .cellPhone("010-1234-5678")
            .email("email@abc.com")
            .memo("I hope to get a loan")
            .zipCode("123456")
            .address("Somewhere in Gangnam-gu, Seoul")
            .addressDetail("What Apartment No. 101, 1st floor No. 101")
            .build();

    Request request = Request.builder()
            .name("Member Kim")
            .cellPhone("010-1234-5678")
            .email("email@abc.com")
            .memo("I hope to get a loan")
            .zipCode("123456")
            .address("Somewhere in Gangnam-gu, Seoul")
            .addressDetail("What Apartment No. 101, 1st floor No. 101")
            .build();

    // when
    when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);

    Response actual = counselService.create(request);

    // then
    assertThat(actual.getName()).isEqualTo(entity.getName());

  }
}
