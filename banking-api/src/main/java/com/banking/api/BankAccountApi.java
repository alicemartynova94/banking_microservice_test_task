package com.banking.api;

import com.banking.dto.BankAccountDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

public interface BankAccountApi {

    @GetMapping("/account/{id}")
    BankAccountDto getAccount(@PathVariable UUID id);

    @PostMapping("/accounts")
    @ResponseStatus(code = HttpStatus.CREATED)
    BankAccountDto addNewAccount(@RequestBody BankAccountDto bankAccountDto);

    @DeleteMapping("/account/{id}")
    void deleteAccount(@PathVariable UUID id);
}
