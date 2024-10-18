package com.banking.banking_microservice_test_task.dao;

import com.banking.banking_microservice_test_task.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

}
