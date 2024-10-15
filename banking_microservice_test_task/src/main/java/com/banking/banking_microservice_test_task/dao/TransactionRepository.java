package com.banking.banking_microservice_test_task.dao;

import com.banking.banking_microservice_test_task.entity.BankAccount;
import com.banking.banking_microservice_test_task.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("SELECT b FROM BankAccount b JOIN b.transaction t WHERE t.id = :transactionId")
    Optional<BankAccount> findByTransactionIdBankAccount(@Param("transactionId") UUID transactionId);

}
