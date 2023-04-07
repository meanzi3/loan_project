package com.example.loan_project.service;

import com.example.loan_project.domain.Counsel;
import com.example.loan_project.dto.CounselDto.Request;
import com.example.loan_project.dto.CounselDto.Response;
import com.example.loan_project.exception.BaseException;
import com.example.loan_project.exception.ResultType;
import com.example.loan_project.repository.CounselRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

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

  @Test // 정상작동하는 경우
  void Should_ReturnResponseOfExistCounselEntity_When_RequestCOunselId(){
    Long findId = 1L;

    Counsel entity = Counsel.builder()
            .counselId(findId)
            .build();

    when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

    Response actual = counselService.get(findId);

    assertThat(actual.getCounselId()).isSameAs(findId);
  }

  @Test // 에러 나는 경우
  void Should_ThrowException_When_RequestNotExistCounselId(){
    Long findId = 2L;

    when(counselRepository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));

    Assertions.assertThrows(BaseException.class, ()-> counselService.get(findId));
  }
}
