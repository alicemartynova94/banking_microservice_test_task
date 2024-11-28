package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.dao.BankAccountRepository;
import com.banking.dto.BankAccountDto;
import com.banking.dto.CurrencyShortnameDto;
import com.banking.bankingmicroservicetask.entity.BankAccount;
import com.banking.bankingmicroservicetask.entity.CurrencyShortname;
import com.banking.bankingmicroservicetask.exceptions.NoSuchBankAccountException;
import com.banking.bankingmicroservicetask.mappers.BankAccountMapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;
@Disabled
@ExtendWith(MockitoExtension.class)
public class BankAccountServiceImplTest {
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
        bankAccount.setCurrencyShortname(currencyShortname);
    }

    @Test
    public void saveAccountExpectAccountIsSaved() {
        given(bankAccountMapper.bankAccountDtoToBankAccount(bankAccountDto)).willReturn(bankAccount);

        bankAccountService.saveAccount(bankAccountDto);

        verify(bankAccountRepository).save(bankAccount);
    }

    @Test
    public void getAccountWithValidIdExpectCorrectMappingAndBankAccountIsReturned() {
        given(bankAccountRepository.findByIdAndBankAccountDeletedTimeIsNull(accountId)).willReturn(Optional.of(bankAccount));
        given(bankAccountMapper.bankAccountToBankAccountDto(bankAccount)).willReturn(bankAccountDto);

        BankAccountDto result = bankAccountService.getAccount(accountId);

        assertThat(result).isEqualTo(bankAccountDto);
    }

    @Test
    public void getAccountWithInvalidIdExpectNoSuchBankAccountException() {
        given(bankAccountRepository.findByIdAndBankAccountDeletedTimeIsNull(accountId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> bankAccountService.getAccount(accountId))
                .isInstanceOf(NoSuchBankAccountException.class);
    }

    @Test
    public void deleteAccountWithValidIdExpectAccountIsDeleted() {
        given(bankAccountRepository.findByIdAndBankAccountDeletedTimeIsNull(accountId)).willReturn(Optional.of(bankAccount));

        bankAccountService.deleteAccount(accountId);

        verify(bankAccountRepository, times(1)).findByIdAndBankAccountDeletedTimeIsNull(accountId);
        verify(bankAccountRepository, times(1)).save(bankAccount);
        assertNotNull(bankAccount.getBankAccountDeletedTime());
    }

    @Test
    public void deleteAccountWithInvalidIdExpectNoSuchBankAccountException() {
        given(bankAccountRepository.findByIdAndBankAccountDeletedTimeIsNull(accountId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> bankAccountService.getAccount(accountId))
                .isInstanceOf(NoSuchBankAccountException.class);

        verify(bankAccountRepository, times(1)).findByIdAndBankAccountDeletedTimeIsNull(accountId);
        verify(bankAccountRepository, never()).deleteById(accountId);
    }
}