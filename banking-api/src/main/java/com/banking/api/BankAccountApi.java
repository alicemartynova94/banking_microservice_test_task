package com.banking.api;

import com.banking.dto.BankAccountDto;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankAccountApi {

    Mono<BankAccountDto> getAccount(UUID id);

    Mono<Void> addNewAccount(BankAccountDto bankAccountDto);

    Mono<Void> deleteAccount(UUID id);

    Flux<BankAccountDto> getAll();
}
