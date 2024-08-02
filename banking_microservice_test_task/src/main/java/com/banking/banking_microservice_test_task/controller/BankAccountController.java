package com.banking.banking_microservice_test_task.controller;

import com.banking.banking_microservice_test_task.dto.BankAccountDto;
import com.banking.banking_microservice_test_task.entity.BankAccount;
import com.banking.banking_microservice_test_task.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/account/{id}")
    public BankAccountDto getAccount(@PathVariable UUID id) {
        return bankAccountService.getAccount(id);
    }

    @PostMapping("/accounts")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BankAccountDto addNewAccount(@RequestBody BankAccountDto bankAccountDto) {
        bankAccountService.saveAccount(bankAccountDto);
        return bankAccountDto;
    }

    @DeleteMapping("/account/{id}")
    public void deleteAccount(@PathVariable UUID id) {
        bankAccountService.deleteAccount(id);
    }
}

