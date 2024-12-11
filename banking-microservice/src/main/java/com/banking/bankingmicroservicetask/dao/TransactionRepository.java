package com.banking.bankingmicroservicetask.dao;

import com.banking.bankingmicroservicetask.entity.Transaction;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository extends ReactiveCrudRepository<Transaction, UUID> {
    Flux<Transaction> findAllByBankAccountIdAndLimitExceededIsTrue(UUID accountId);

    Mono<Transaction> findByIdAndTransactionDeletedTimeIsNull(UUID id);
}
