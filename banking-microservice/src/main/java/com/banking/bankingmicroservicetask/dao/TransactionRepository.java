package com.banking.bankingmicroservicetask.dao;

import com.banking.bankingmicroservicetask.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository extends ReactiveCrudRepository<Transaction, UUID> {
    Flux<Transaction> findAllByBankAccountIdAndLimitExceededIsTrue(UUID accountId);

    Mono<Transaction> findByIdAndTransactionDeletedTimeIsNull(UUID id);
}
