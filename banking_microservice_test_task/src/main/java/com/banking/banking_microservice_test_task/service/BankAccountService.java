package com.banking.banking_microservice_test_task.service;

import com.banking.banking_microservice_test_task.entity.BankAccount;

import java.util.UUID;

public interface BankAccountService {
    public void saveAccount(BankAccount bankAccount);

    public BankAccount getAccount(UUID id);

    public void deleteAccount(UUID id);
}
