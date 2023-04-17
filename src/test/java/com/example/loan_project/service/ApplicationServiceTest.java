package com.example.loan_project.service;

import com.example.loan_project.domain.AcceptTerms;
import com.example.loan_project.domain.Application;
import com.example.loan_project.domain.Terms;
import com.example.loan_project.dto.ApplicationDto;
import com.example.loan_project.dto.ApplicationDto.Request;
import com.example.loan_project.dto.ApplicationDto.Response;
import com.example.loan_project.exception.BaseException;
import com.example.loan_project.repository.AcceptTermsRepository;
import com.example.loan_project.repository.ApplicationRepository;
import com.example.loan_project.repository.TermsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

  @InjectMocks
  ApplicationServiceImpl applicationService;

  @Mock
  private ApplicationRepository applicationRepository;

  @Mock
  private TermsRepository termsRepository;

  @Mock
  private AcceptTermsRepository acceptTermsRepository;

  @Spy
  private ModelMapper modelMapper;

  @Test
  void Should_ReturnResponseOfNewApplicationEntity_When_RequestCreateApplication(){

    Application entity = Application.builder()
            .name("Member A")
            .cellPhone("010-1234-5678")
            .email("mail@gmail.com")
            .hopeAmount(BigDecimal.valueOf(50000000))
            .build();

    Request request = Request.builder()
            .name("Member A")
            .cellPhone("010-1234-5678")
            .email("mail@gmail.com")
            .hopeAmount(BigDecimal.valueOf(50000000))
            .build();

    when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);

    Response actual = applicationService.create(request);

    assertThat(actual.getHopeAmount()).isSameAs(entity.getHopeAmount());
    assertThat(actual.getName()).isSameAs(entity.getName());
  }

  @Test
  void Should_ReturnResponseOfExistApplicationEntity_When_RequestExistApplication(){

    // given
    Long findId = 1L;

    Application entity = Application.builder()
            .applicationId(1L)
            .build();

    // when
    when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

    Response actual = applicationService.get(findId);

    // then
    assertThat(actual.getApplicationId()).isSameAs(findId);
  }

  @Test
  void Should_ReturnUpdateResponseOfExistApplicationEntity_When_RequestUpdateExistApplicationInfo(){

    // given
    Long findId = 1L;

    Application entity = Application.builder()
            .applicationId(1L)
            .hopeAmount(BigDecimal.valueOf(50000000))
            .build();

    Request request = Request.builder()
            .hopeAmount(BigDecimal.valueOf(5000000))
            .build();

    // when
    when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);
    when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

    Response actual = applicationService.update(findId, request);


    // then
    assertThat(actual.getApplicationId()).isSameAs(findId);
    assertThat(actual.getHopeAmount()).isSameAs(request.getHopeAmount());
  }

  @Test
  void Should_DeleteApplicationEntity_When_RequestDeleteExistApplicationInfo(){

    // given
    Long targetId = 1L;

    Application entity = Application.builder()
            .applicationId(1L)
            .build();

    // when
    when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);
    when(applicationRepository.findById(targetId)).thenReturn(Optional.ofNullable(entity));

    applicationService.delete(targetId);

    // then
    assertThat(entity.getIsDeleted()).isSameAs(true);
  }

  @Test
  void Should_AddAcceptTerms_When_RequestAcceptTermsOfApplication() {

    // given
    Terms entityA = Terms.builder()
            .termsId(1L)
            .name("대출 이용 약관 1")
            .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdddads")
            .build();

    Terms entityB = Terms.builder()
            .termsId(2L)
            .name("대출 이용 약관 2")
            .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdweqwq")
            .build();

    List<Long> acceptTerms = Arrays.asList(1L, 2L);

    ApplicationDto.AcceptTerms request = ApplicationDto.AcceptTerms.builder()
            .acceptTermsIds(acceptTerms)
            .build();

    Long findId = 1L;

    // when
    when(applicationRepository.findById(findId)).thenReturn(
            Optional.ofNullable(Application.builder().build()));
    when(termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId"))).thenReturn(Arrays.asList(entityA, entityB));
    when(acceptTermsRepository.save(ArgumentMatchers.any(AcceptTerms.class))).thenReturn(AcceptTerms.builder().build());

    // then
    Boolean actual = applicationService.acceptTerms(findId, request);
    assertThat(actual).isTrue();
  }

  @Test
  void Should_ThrowException_When_RequestNotAllAcceptTermsOfApplication() {

    // given
    Terms entityA = Terms.builder()
            .termsId(1L)
            .name("대출 이용 약관 1")
            .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdddads")
            .build();

    Terms entityB = Terms.builder()
            .termsId(2L)
            .name("대출 이용 약관 2")
            .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdweqwq")
            .build();

    List<Long> acceptTerms = Arrays.asList(1L);

    ApplicationDto.AcceptTerms request = ApplicationDto.AcceptTerms.builder()
            .acceptTermsIds(acceptTerms)
            .build();

    Long findId = 1L;

    // when
    when(applicationRepository.findById(findId)).thenReturn(
            Optional.ofNullable(Application.builder().build()));
    when(termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId"))).thenReturn(Arrays.asList(entityA, entityB));

    // then
    Assertions.assertThrows(BaseException.class, () -> applicationService.acceptTerms(findId, request));
  }

  @Test
  void Should_ThrowException_When_RequestNotExistAcceptTermsOfApplication() {

    // given
    Terms entityA = Terms.builder()
            .termsId(1L)
            .name("대출 이용 약관 1")
            .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdddads")
            .build();

    Terms entityB = Terms.builder()
            .termsId(2L)
            .name("대출 이용 약관 2")
            .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdweqwq")
            .build();

    List<Long> acceptTerms = Arrays.asList(1L, 3L);

    ApplicationDto.AcceptTerms request = ApplicationDto.AcceptTerms.builder()
            .acceptTermsIds(acceptTerms)
            .build();

    Long findId = 1L;

    // when
    when(applicationRepository.findById(findId)).thenReturn(
            Optional.ofNullable(Application.builder().build()));
    when(termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId"))).thenReturn(Arrays.asList(entityA, entityB));

    // then
    Assertions.assertThrows(BaseException.class, () -> applicationService.acceptTerms(findId, request));
  }
}
