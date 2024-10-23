package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.dto.TransactionDto;

import java.util.UUID;

public interface TransactionService {

    public void saveTransaction(TransactionDto transactionDto);

    public TransactionDto getTransaction(UUID id);

    public void deleteTransaction(UUID id);
}
