package com.banking.bankingmicroservicetask.dao;

import com.banking.bankingmicroservicetask.entity.TransactionLimit;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionLimitRepository extends JpaRepository<TransactionLimit, UUID> {
    @Query("SELECT limit FROM TransactionLimit limit WHERE limit.id = :id AND limit.limitDeletedTime IS NULL")
    Optional<TransactionLimit> findByIdActiveLimit(@Param("id") UUID id);
}
