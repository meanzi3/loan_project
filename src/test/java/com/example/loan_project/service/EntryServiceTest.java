package com.example.loan_project.service;

import com.example.loan_project.domain.Application;
import com.example.loan_project.domain.Entry;
import com.example.loan_project.dto.EntryDto;
import com.example.loan_project.dto.EntryDto.Response;
import com.example.loan_project.dto.EntryDto.Request;
import com.example.loan_project.repository.ApplicationRepository;
import com.example.loan_project.repository.EntryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EntryServiceTest {

  @InjectMocks
  private EntryServiceImpl entryService;

  @Mock
  private EntryRepository entryRepository;

  @Mock
  private ApplicationRepository applicationRepository;

  @Mock
  private BalanceServiceImpl balanceService;

  @Spy
  private ModelMapper modelMapper;

  @Test
  void Should_ReturnResponseOfNewEntryEntity_When_RequestNewEntry(){

    Entry entry = Entry.builder()
            .entryId(1L)
            .applicationId(1L)
            .entryAmount(BigDecimal.valueOf(5000000))
            .build();

    Request request = Request.builder()
            .entryAmount(BigDecimal.valueOf(5000000))
            .build();

    // contract
    when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(Application.builder().contractedAt(LocalDateTime.now()).build()));

    // entry save
    when(entryRepository.save(ArgumentMatchers.any(Entry.class))).thenReturn(entry);

    Response actual = entryService.create(1L, request);

    assertThat(actual.getApplicationId()).isSameAs(entry.getApplicationId());
    assertThat(actual.getEntryAmount()).isEqualTo(entry.getEntryAmount());

  }

  @Test
  void Should_ReturnResponseExistEntryEntity_When_RequestExistEntryId(){
    Entry entry = Entry.builder()
            .entryId(1L)
            .build();
    when(entryRepository.findByApplicationId(1L)).thenReturn(Optional.ofNullable(entry));
    Response actual = entryService.get(1L);

    assertThat(actual.getEntryId()).isSameAs(1L);
  }

  @Test
  void Should_ReturnUpdatedResponseOfExistEntryEntity_When_RequestUpdateExistEntryInto(){
    Entry entry = Entry.builder()
            .entryId(1L)
            .applicationId(1L)
            .entryAmount(BigDecimal.valueOf(5000000))
            .build();

    Request request = Request.builder()
            .entryAmount(BigDecimal.valueOf(3000000))
            .build();

    when(entryRepository.findById(1L)).thenReturn(Optional.ofNullable(entry));
    when(entryRepository.save(ArgumentMatchers.any(Entry.class))).thenReturn(entry);

    BigDecimal beforeEntryAmount = entry.getEntryAmount();

    EntryDto.UpdateResponse updateResponse = entryService.update(1L, request);

    assertThat(updateResponse.getEntryId()).isSameAs(1L);
    assertThat(updateResponse.getBeforeEntryAmount()).isSameAs(beforeEntryAmount);
    assertThat(updateResponse.getAfterEntryAmount()).isSameAs(request.getEntryAmount());

  }

  @Test
  void Should_DeleteEntryEntity_When_RequestDeleteExistEntryInfo(){
    Entry entry = Entry.builder()
            .entryId(1L)
            .build();

    when(entryRepository.findById(1L)).thenReturn(Optional.ofNullable(entry));
    when(entryRepository.save(ArgumentMatchers.any(Entry.class))).thenReturn(entry);

    entryService.delete(1L);

    assertThat(entry.getIsDeleted()).isTrue();
  }
}