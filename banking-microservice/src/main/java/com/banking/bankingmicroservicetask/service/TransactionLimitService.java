package com.banking.bankingmicroservicetask.service;

import com.banking.dto.TransactionLimitDto;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface TransactionLimitService {

  Mono<Void> saveLimit(TransactionLimitDto limitDto);

  Mono<TransactionLimitDto> getLimit(UUID id);

  Mono<Void> updateLimit(UUID id, TransactionLimitDto transactionLimitDto);

  Mono<Void> deleteLimit(UUID id);
}
