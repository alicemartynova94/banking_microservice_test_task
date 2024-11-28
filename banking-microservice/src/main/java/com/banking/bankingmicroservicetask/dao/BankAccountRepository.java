package com.banking.bankingmicroservicetask.dao;

import com.banking.bankingmicroservicetask.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    Optional<BankAccount> findByIdAndBankAccountDeletedTimeIsNull(UUID id);
}
