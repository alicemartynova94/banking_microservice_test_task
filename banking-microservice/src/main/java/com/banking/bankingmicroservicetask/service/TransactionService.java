package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.entity.Transaction;
import com.banking.dto.TransactionDto;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<Void> saveTransaction(TransactionDto transactionDto);

    Mono<TransactionDto> getTransaction(UUID id);
    Flux<Transaction> getExceededLimitTransactions(UUID accountId);

    Mono<Void> deleteTransaction(UUID id);
}
