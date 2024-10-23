package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.dto.TransactionDto;
import com.banking.bankingmicroservicetask.entity.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    public void saveTransaction(TransactionDto transactionDto);

    public TransactionDto getTransaction(UUID id);
    public List<Transaction> getExceededLimitTransactions(UUID accountId);

    public void deleteTransaction(UUID id);
}
