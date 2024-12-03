package com.banking.bankingmicroservicetask.service;

import com.banking.dto.BankAccountDto;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankAccountService {
    Mono<Void> saveAccount(BankAccountDto bankAccountDto);

    Mono<BankAccountDto> getAccount(UUID id);

    Mono<Void> deleteAccount(UUID id);

    Flux<BankAccountDto> getAll();
}
