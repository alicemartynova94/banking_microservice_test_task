package com.banking.banking_microservice_test_task.dao;

import com.banking.banking_microservice_test_task.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
}
