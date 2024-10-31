package com.banking.bankingmicroservicetask.controller;

import com.banking.api.BankAccountApi;
import com.banking.dto.BankAccountDto;
import com.banking.bankingmicroservicetask.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BankAccountController implements BankAccountApi {

    private final BankAccountService bankAccountService;

    @Override
    @GetMapping("/account/{id}")
    public BankAccountDto getAccount(@PathVariable UUID id) {
        return bankAccountService.getAccount(id);
    }

    @Override
    @PostMapping("/accounts")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BankAccountDto addNewAccount(@RequestBody BankAccountDto bankAccountDto) {
        bankAccountService.saveAccount(bankAccountDto);
        return bankAccountDto;
    }

    @Override
    @DeleteMapping("/account/{id}")
    public void deleteAccount(@PathVariable UUID id) {
        bankAccountService.deleteAccount(id);
    }
}

