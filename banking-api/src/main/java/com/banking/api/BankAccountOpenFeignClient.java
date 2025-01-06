package com.banking.api;

import com.banking.dto.BankAccountDto;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestBody;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "banking-microservice")
public interface BankAccountOpenFeignClient extends BankAccountApi {

    @Override
    @RequestLine("GET /api/accounts/{id}")
    Mono<BankAccountDto> getAccount(@Param("id") UUID id);

    @RequestLine("GET /api/accounts")
    Flux<BankAccountDto> getAll();

    @Override
    @RequestLine("POST /api/accounts")
    @Headers("Content-Type: application/json")
    Mono<Void> addNewAccount(@RequestBody BankAccountDto bankAccountDto);

    @Override
    @RequestLine("DELETE /api/accounts/{id}")
    Mono<Void> deleteAccount(@Param("id") UUID id);
}
