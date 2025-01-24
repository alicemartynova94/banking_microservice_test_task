package com.banking.bankingmicroservicetask.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.banking.bankingmicroservicetask.dao.TransactionLimitRepository;
import com.banking.bankingmicroservicetask.entity.TransactionLimit;
import com.banking.bankingmicroservicetask.exceptions.LimitUpdateFrequencyExceededException;
import com.banking.bankingmicroservicetask.exceptions.LimitUpdateNotAllowedException;
import com.banking.dto.TransactionLimitDto;
import com.banking.enums.TransactionCategory;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class TransactionLimitServiceImplTest {
    @InjectMocks
    TransactionLimitServiceImpl transactionLimitService;
    @Mock
    private TransactionLimitRepository transactionLimitRepository;

    private TransactionLimit transactionLimit;
    private TransactionLimitDto transactionLimitDto;
    private UUID uuid;

    @BeforeEach
    void init() {
        uuid = UUID.randomUUID();

        transactionLimit = new TransactionLimit();
        transactionLimit.setId(uuid);
        transactionLimit.setLimitSum(1000.00);
        transactionLimit.setTransactionCategory(TransactionCategory.SERVICES);

        transactionLimitDto = new TransactionLimitDto();
        transactionLimitDto.setId(uuid);
        transactionLimitDto.setLimitSum(1500.00);
        transactionLimitDto.setTransactionCategory(TransactionCategory.SERVICES);
    }

    @Test
    void updateLimit_On15thLastUpdate2MonthOneTime_ExpectSuccessfulUpdate() {
        LocalDateTime todayFixedDate = LocalDateTime.of(2024, 3, 15, 10, 30);
        LocalDateTime todayMinusMonth = todayFixedDate.minusMonths(2);

        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(todayFixedDate);

      given(transactionLimitRepository.findByIdAndLimitDeletedTimeIsNull(uuid))
          .willReturn(Mono.just(transactionLimit));
      given(transactionLimitRepository.save(any(TransactionLimit.class)))
          .willAnswer(invocation -> Mono.just(invocation.getArgument(0)));

      transactionLimit.setLimitLastUpdateTime(todayMinusMonth);
      StepVerifier.create(transactionLimitService.updateLimit(transactionLimitDto.getId(), transactionLimitDto))
          .verifyComplete();
      verify(transactionLimitRepository, times(1)).save(transactionLimit);
      Assertions.assertEquals(transactionLimitDto.getLimitSum(), transactionLimit.getLimitSum());
    }
  }

  @Test
  void updateLimit_On15thLastUpdateSameMonth_ExpectLimitUpdateFrequencyExceededException() {
    LocalDateTime todayFixedDate = LocalDateTime.of(2024, 3, 15, 10, 30);

        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(todayFixedDate);

      given(transactionLimitRepository.findByIdAndLimitDeletedTimeIsNull(uuid))
          .willReturn(Mono.just(transactionLimit));

      transactionLimit.setLimitLastUpdateTime(todayFixedDate);

      StepVerifier.create(transactionLimitService.updateLimit(transactionLimitDto.getId(), transactionLimitDto))
          .expectError(LimitUpdateFrequencyExceededException.class)
          .verify();
      verify(transactionLimitRepository, never()).save(transactionLimit);
    }
  }

    @Test
    void updateLimit_On17thLastUpdate1MonthOneTime_ExpectLimitUpdateNotAllowedException() {
        LocalDateTime todayWrongDate = LocalDateTime.of(2024, 3, 17, 10, 30);
        LocalDateTime updateDate = todayWrongDate.minusMonths(1).minusDays(2);

        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(todayWrongDate);

      given(transactionLimitRepository.findByIdAndLimitDeletedTimeIsNull(uuid)).willReturn(
          Mono.just(transactionLimit));

      transactionLimit.setLimitLastUpdateTime(updateDate);
      StepVerifier.create(transactionLimitService.updateLimit(transactionLimitDto.getId(), transactionLimitDto))
          .expectError(LimitUpdateNotAllowedException.class)
          .verify();

      verify(transactionLimitRepository, never()).save(any());
    }
  }

    @Test
    void updateLimit_LessThanOneMonthAgoOneTime_ExpectLimitUpdateNotAllowedException() {
        LocalDateTime todayFixedDate = LocalDateTime.of(2024, 3, 5, 10, 30);
        LocalDateTime wrongUpdateDate = todayFixedDate.minusMonths(1).plusDays(15);

        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(todayFixedDate);

      given(transactionLimitRepository.findByIdAndLimitDeletedTimeIsNull(uuid)).willReturn(
          Mono.just(transactionLimit));

      transactionLimit.setLimitLastUpdateTime(wrongUpdateDate);
      StepVerifier.create(transactionLimitService.updateLimit(transactionLimitDto.getId(), transactionLimitDto))
          .expectError(LimitUpdateNotAllowedException.class)
          .verify();

      verify(transactionLimitRepository, never()).save(any());
    }
  }
}
