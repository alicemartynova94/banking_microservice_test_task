package com.banking.bankingmicroservicetask;

import com.banking.bankingmicroservicetask.dao.BankAccountRepository;
import com.banking.bankingmicroservicetask.dto.BankAccountDto;
import com.banking.bankingmicroservicetask.dto.CurrencyShortnameDto;
import com.banking.bankingmicroservicetask.entity.BankAccount;
import com.banking.bankingmicroservicetask.entity.CurrencyShortname;
import com.banking.bankingmicroservicetask.exception_handling.NoSuchBankAccountException;
import com.banking.bankingmicroservicetask.mappers.BankAccountMapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.banking.bankingmicroservicetask.service.BankAccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

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
        given(bankAccountRepository.findById(accountId)).willReturn(Optional.of(bankAccount));
        given(bankAccountMapper.bankAccountToBankAccountDto(bankAccount)).willReturn(bankAccountDto);

        BankAccountDto result = bankAccountService.getAccount(accountId);

        assertThat(result).isEqualTo(bankAccountDto);
    }

    @Test
    public void getAccountWithInvalidIdExpectNoSuchBankAccountException() {
        given(bankAccountRepository.findById(accountId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> bankAccountService.getAccount(accountId))
                .isInstanceOf(NoSuchBankAccountException.class);
    }

    @Test
    public void deleteAccountWithValidIdExpectAccountIsDeleted() {
        given(bankAccountRepository.findById(accountId)).willReturn(Optional.of(bankAccount));

        bankAccountService.deleteAccount(accountId);

        verify(bankAccountRepository, times(1)).findById(accountId);
        verify(bankAccountRepository, times(1)).deleteById(accountId);
    }

    @Test
    public void deleteAccountWithInvalidIdExpectNoSuchBankAccountException() {
        given(bankAccountRepository.findById(accountId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> bankAccountService.getAccount(accountId))
                .isInstanceOf(NoSuchBankAccountException.class);

        verify(bankAccountRepository, times(1)).findById(accountId);
        verify(bankAccountRepository, never()).deleteById(accountId);
    }
}