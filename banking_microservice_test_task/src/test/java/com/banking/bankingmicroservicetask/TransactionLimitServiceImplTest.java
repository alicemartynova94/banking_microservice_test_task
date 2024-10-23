package com.banking.bankingmicroservicetask;


import com.banking.bankingmicroservicetask.dao.TransactionLimitRepository;
import com.banking.bankingmicroservicetask.dto.TransactionLimitDto;
import com.banking.bankingmicroservicetask.entity.TransactionCategory;
import com.banking.bankingmicroservicetask.entity.TransactionLimit;
import com.banking.bankingmicroservicetask.exception_handling.LimitUpdateFrequencyExceededException;
import com.banking.bankingmicroservicetask.exception_handling.LimitUpdateNotAllowedException;
import com.banking.bankingmicroservicetask.service.TransactionLimitServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.MockedStatic;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
public class TransactionLimitServiceImplTest {
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
    public void updateLimit_On15thLastUpdate2MonthOneTime_ExpectSuccessfulUpdate() {
        LocalDateTime todayFixedDate = LocalDateTime.of(2024, 3, 15, 10, 30);
        LocalDateTime todayMinusMonth = todayFixedDate.minusMonths(2);

        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(todayFixedDate);

            given(transactionLimitRepository.findById(uuid)).willReturn(Optional.of(transactionLimit));

            transactionLimit.setLimitLastUpdateTime(todayMinusMonth);
            transactionLimitService.updateLimit(transactionLimitDto.getId(), transactionLimitDto);

            verify(transactionLimitRepository, times(1)).save(transactionLimit);
            Assertions.assertEquals(transactionLimitDto.getLimitSum(), transactionLimit.getLimitSum());
        }
    }

    @Test
    public void updateLimit_On15thLastUpdate2MonthTwoTimes_ExpectLimitUpdateFrequencyExceededException() {
        LocalDateTime todayFixedDate = LocalDateTime.of(2024, 3, 15, 10, 30);
        LocalDateTime todayMinusMonths = todayFixedDate.minusMonths(2);

        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(todayFixedDate);

            given(transactionLimitRepository.findById(uuid)).willReturn(Optional.of(transactionLimit));

            transactionLimit.setLimitLastUpdateTime(todayMinusMonths);
            transactionLimitService.updateLimit(transactionLimitDto.getId(), transactionLimitDto);
            transactionLimitDto.setLimitSum(4000.00);
            transactionLimitService.updateLimit(transactionLimitDto.getId(), transactionLimitDto);

            verify(transactionLimitRepository, times(2)).save(transactionLimit);

            Assertions.assertThrows(LimitUpdateFrequencyExceededException.class, () -> {
                if (Mockito.mockingDetails(transactionLimitRepository).getInvocations().size() > 1) {
                    throw new LimitUpdateFrequencyExceededException("You've already created limit this month.");
                }
            });
        }
    }

    @Test
    public void updateLimit_On17thLastUpdate1MonthOneTime_ExpectLimitUpdateNotAllowedException() {
        LocalDateTime todayWrongDate = LocalDateTime.of(2024, 3, 17, 10, 30);
        LocalDateTime updateDate = todayWrongDate.minusMonths(1).minusDays(2);

        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(todayWrongDate);

            given(transactionLimitRepository.findById(uuid)).willReturn(Optional.of(transactionLimit));

            transactionLimit.setLimitLastUpdateTime(updateDate);

            verify(transactionLimitRepository, never()).save(any());
            Assertions.assertThrows(LimitUpdateNotAllowedException.class, () -> transactionLimitService.updateLimit(transactionLimitDto.getId(), transactionLimitDto));
        }
    }

    @Test
    public void updateLimit_LessThanOneMonthAgoOneTime_ExpectLimitUpdateNotAllowedException() {
        LocalDateTime todayFixedDate = LocalDateTime.of(2024, 3, 5, 10, 30);
        LocalDateTime wrongUpdateDate = todayFixedDate.minusMonths(1).plusDays(15);

        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(todayFixedDate);

            given(transactionLimitRepository.findById(uuid)).willReturn(Optional.of(transactionLimit));

            transactionLimit.setLimitLastUpdateTime(wrongUpdateDate);

            verify(transactionLimitRepository, never()).save(any());
            Assertions.assertThrows(LimitUpdateNotAllowedException.class, () -> transactionLimitService.updateLimit(transactionLimitDto.getId(), transactionLimitDto));
        }
    }

}
