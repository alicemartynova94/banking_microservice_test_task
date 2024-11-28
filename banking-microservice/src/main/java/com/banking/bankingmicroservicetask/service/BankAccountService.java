package com.banking.bankingmicroservicetask.service;

import com.banking.dto.BankAccountDto;

import java.util.List;
import java.util.UUID;

public interface BankAccountService {
    public void saveAccount(BankAccountDto bankAccountDto);

    public BankAccountDto getAccount(UUID id);

    public void deleteAccount(UUID id);

    public List<BankAccountDto> getAll();
}
