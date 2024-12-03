package com.banking.bankingmicroservicetask.dao;

import com.banking.bankingmicroservicetask.entity.BankAccount;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface BankAccountRepository extends ReactiveCrudRepository<BankAccount, UUID> {
    Mono<BankAccount> findByIdAndBankAccountDeletedTimeIsNull(UUID id);
}
