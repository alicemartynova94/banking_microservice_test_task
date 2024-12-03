package com.banking.api;

import com.banking.dto.BankAccountDto;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "banking-microservice")
public interface BankAccountOpenFeignClient extends BankAccountApi {

    @Override
    @GetMapping("/api/accounts/{id}")
    BankAccountDto getAccount(@PathVariable("id") UUID id);

    @GetMapping("/api/accounts")
    List<BankAccountDto> getAll();

    @Override
    @PostMapping("/api/accounts")
    BankAccountDto addNewAccount(@RequestBody BankAccountDto bankAccountDto);

    @Override
    @DeleteMapping("/api/accounts/{id}")
    void deleteAccount(@PathVariable("id") UUID id);
}
