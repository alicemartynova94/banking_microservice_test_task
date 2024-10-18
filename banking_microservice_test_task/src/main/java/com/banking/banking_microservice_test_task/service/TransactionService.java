package com.banking.banking_microservice_test_task.service;

import com.banking.banking_microservice_test_task.dto.TransactionDto;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    public void saveTransaction(TransactionDto transactionDto);

    public TransactionDto getTransaction(UUID id);

    public void deleteTransaction(UUID id);
}
