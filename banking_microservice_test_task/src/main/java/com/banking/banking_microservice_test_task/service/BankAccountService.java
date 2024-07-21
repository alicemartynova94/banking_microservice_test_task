package com.banking.banking_microservice_test_task.service;

import com.banking.banking_microservice_test_task.dto.BankAccountDto;
import com.banking.banking_microservice_test_task.entity.BankAccount;

import java.util.UUID;

public interface BankAccountService {
    public void saveAccount(BankAccountDto bankAccountDto);

    public BankAccountDto getAccount(UUID id);

    public void deleteAccount(UUID id);
}
