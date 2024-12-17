package com.banking.bankingmicroservicetask.dao;

import com.banking.bankingmicroservicetask.entity.TransactionLimit;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface TransactionLimitRepository extends ReactiveCrudRepository<TransactionLimit, UUID> {

    Mono<TransactionLimit> findByIdAndLimitDeletedTimeIsNull(UUID id);
}
