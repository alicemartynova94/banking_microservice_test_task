package com.banking.bankingmicroservicetask.dao;

import com.banking.bankingmicroservicetask.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    @Query(value = "SELECT * FROM transaction t WHERE t.bank_account_id = :accountId AND t.limit_exceeded = true", nativeQuery = true)
    List<Transaction> findExceededLimitTransactionsByBankAccountId(@Param("accountId") UUID accountId);
}
