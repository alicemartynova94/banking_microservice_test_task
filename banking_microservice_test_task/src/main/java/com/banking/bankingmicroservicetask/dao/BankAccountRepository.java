package com.banking.bankingmicroservicetask.dao;

import com.banking.bankingmicroservicetask.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {

    @Query("SELECT account FROM BankAccount account WHERE account.id = :id AND account.bankAccountDeletedTime IS NULL")
    Optional<BankAccount> findByIdActiveAccount(@Param("id") UUID id);
}
