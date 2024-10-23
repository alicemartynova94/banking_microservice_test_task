package com.banking.bankingmicroservicetask.dao;

import com.banking.bankingmicroservicetask.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

}
