package com.banking;

import com.banking.api.BankAccountApi;
import com.banking.dto.BankAccountDto;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BankAccountClient implements BankAccountApi {
  @Override
  public Mono<BankAccountDto> getAccount(UUID id) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> addNewAccount(BankAccountDto bankAccountDto) {
    return Mono.empty();
  }

  @Override
  public Mono<Void> deleteAccount(UUID id) {
    return Mono.empty();
  }

  @Override
  public Flux<BankAccountDto> getAll() {
    return Flux.empty();
  }
}
