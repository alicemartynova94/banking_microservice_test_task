package com.banking.bankingmicroservicetask.dao;

import com.banking.bankingmicroservicetask.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllByBankAccountIdAndLimitExceededIsTrue(UUID accountId);

    Optional<Transaction> findByIdAndTransactionDeletedTimeIsNull(UUID id);
}
