package com.banking.bankingmicroservicetask.service;

import com.banking.dto.TransactionDto;
import com.banking.bankingmicroservicetask.entity.Transaction;

import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    public Mono<Void> saveTransaction(TransactionDto transactionDto);

    public Mono<TransactionDto> getTransaction(UUID id);
    public Flux<Transaction> getExceededLimitTransactions(UUID accountId);

    public Mono<Void> deleteTransaction(UUID id);
}
