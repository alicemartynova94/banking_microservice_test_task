package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.dao.BankAccountRepository;
import com.banking.dto.BankAccountDto;
import com.banking.dto.CurrencyShortnameDto;
import com.banking.bankingmicroservicetask.entity.BankAccount;
import com.banking.bankingmicroservicetask.entity.CurrencyShortname;
import com.banking.bankingmicroservicetask.exceptions.NoSuchBankAccountException;
import com.banking.bankingmicroservicetask.mappers.BankAccountMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {
    @InjectMocks
    private BankAccountServiceImpl bankAccountService;
    @Mock
    private BankAccountRepository bankAccountRepository;
    @Mock
    private BankAccountMapper bankAccountMapper;

    private UUID accountId;
    private BankAccountDto bankAccountDto;
    private BankAccount bankAccount;

    @BeforeEach
    void init() {
        UUID currencyId = UUID.randomUUID();
        Short currencyName = (short) 1;

        CurrencyShortnameDto currencyShortnameDto = new CurrencyShortnameDto();
        currencyShortnameDto.setId(currencyId);
        currencyShortnameDto.setCurrencyNameNumeric(currencyName);

        CurrencyShortname currencyShortname = new CurrencyShortname();
        currencyShortname.setId(currencyId);
        currencyShortname.setCurrencyNameNumeric(currencyName);

        accountId = UUID.randomUUID();
        Integer accountNumber = 1111111;
        String accountHolder = "Smirnov Dmitry";
        Double availableFunds = 0.0;

        bankAccountDto = new BankAccountDto();
        bankAccountDto.setId(accountId);
        bankAccountDto.setAccountNumber(accountNumber);
        bankAccountDto.setAccountHolder(accountHolder);
        bankAccountDto.setAvailableFunds(availableFunds);
        bankAccountDto.setCurrencyShortname(currencyShortnameDto);

        bankAccount = new BankAccount();
        bankAccount.setId(accountId);
        bankAccount.setAccountNumber(accountNumber);
        bankAccount.setAccountHolder(accountHolder);
        bankAccount.setAvailableFunds(availableFunds);
        bankAccount.setCurrencyShortnameId(currencyShortname.getId());
    }

    @Test
    void saveAccountExpectAccountIsSaved() {
        given(bankAccountMapper.bankAccountDtoToBankAccount(bankAccountDto)).willReturn(bankAccount);
        given(bankAccountRepository.save(bankAccount)).willReturn(Mono.just(bankAccount));

        StepVerifier.create(bankAccountService.saveAccount(bankAccountDto))
            .verifyComplete();

        verify(bankAccountRepository).save(bankAccount);
    }

    @Test
    void getAccountWithValidIdExpectCorrectMappingAndBankAccountIsReturned() {
        given(bankAccountRepository.findByIdAndBankAccountDeletedTimeIsNull(accountId))
            .willReturn(Mono.just(bankAccount));
        given(bankAccountMapper.bankAccountToBankAccountDto(bankAccount))
            .willReturn(bankAccountDto);

        StepVerifier.create(bankAccountService.getAccount(accountId))
            .expectNext(bankAccountDto)
            .verifyComplete();

        verify(bankAccountRepository, times(1))
            .findByIdAndBankAccountDeletedTimeIsNull(accountId);
    }

    @Test
    void getAccountWithInvalidIdExpectNoSuchBankAccountException() {
        given(bankAccountRepository.findByIdAndBankAccountDeletedTimeIsNull(accountId))
            .willReturn(Mono.empty());

        StepVerifier.create(bankAccountService.getAccount(accountId))
            .expectError(NoSuchBankAccountException.class)
            .verify();

        verify(bankAccountRepository, times(1))
            .findByIdAndBankAccountDeletedTimeIsNull(accountId);
    }

    @Test
    void deleteAccountWithValidIdExpectAccountIsDeleted() {
        given(bankAccountRepository.findByIdAndBankAccountDeletedTimeIsNull(accountId))
            .willReturn(Mono.just(bankAccount));
        given(bankAccountRepository.save(bankAccount)).willReturn(Mono.just(bankAccount));

        StepVerifier.create(bankAccountService.deleteAccount(accountId))
            .verifyComplete();

        verify(bankAccountRepository, times(1))
            .findByIdAndBankAccountDeletedTimeIsNull(accountId);
        verify(bankAccountRepository, times(1)).save(bankAccount);
    }

    @Test
    void deleteAccountWithInvalidIdExpectNoSuchBankAccountException() {
        given(bankAccountRepository.findByIdAndBankAccountDeletedTimeIsNull(accountId))
            .willReturn(Mono.empty());

        StepVerifier.create(bankAccountService.deleteAccount(accountId))
            .expectError(NoSuchBankAccountException.class)
            .verify();

        verify(bankAccountRepository, times(1))
            .findByIdAndBankAccountDeletedTimeIsNull(accountId);
        verify(bankAccountRepository, never()).save(any());
    }
}
