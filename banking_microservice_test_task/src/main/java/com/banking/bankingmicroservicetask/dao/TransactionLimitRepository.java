package com.banking.bankingmicroservicetask.dao;

import com.banking.bankingmicroservicetask.entity.TransactionLimit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionLimitRepository extends JpaRepository<TransactionLimit, UUID> {
}
