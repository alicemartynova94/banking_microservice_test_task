package com.banking.api;

import com.banking.dto.BankAccountDto;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "banking-microservice")
public interface BankAccountOpenFeignClient extends BankAccountApi {

    @Override
    @GetMapping("/api/accounts/{id}")
    Mono<BankAccountDto> getAccount(@PathVariable("id") UUID id);

    @GetMapping("/api/accounts")
    Flux<BankAccountDto> getAll();

    @Override
    @PostMapping("/api/accounts")
    Mono<Void> addNewAccount(@RequestBody BankAccountDto bankAccountDto);

    @Override
    @DeleteMapping("/api/accounts/{id}")
    Mono<Void> deleteAccount(@PathVariable("id") UUID id);
}
