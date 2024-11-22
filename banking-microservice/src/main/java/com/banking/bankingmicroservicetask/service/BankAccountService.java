package com.banking.bankingmicroservicetask.service;

import com.banking.dto.BankAccountDto;

import java.util.UUID;

public interface BankAccountService {
    public void saveAccount(BankAccountDto bankAccountDto);

    public BankAccountDto getAccount(UUID id);

    public void deleteAccount(UUID id);
}
