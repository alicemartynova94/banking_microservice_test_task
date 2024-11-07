package com.banking.bankingmicroservicetask.controller;

import com.banking.bankingmicroservicetask.dto.BankAccountDto;
import com.banking.bankingmicroservicetask.service.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Bank Account API", description = "Operations related to bank accounts.")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @Operation(summary = "Get a bank account",
            description = "Get an existing bank account by id.")
    @GetMapping("/account/{id}")
    public BankAccountDto getAccount(@PathVariable UUID id) {
        return bankAccountService.getAccount(id);
    }

    @Operation(summary = "Save a bank account",
            description = "Save a new bank account into database.")
    @PostMapping("/accounts")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BankAccountDto addNewAccount(@RequestBody BankAccountDto bankAccountDto) {
        bankAccountService.saveAccount(bankAccountDto);
        return bankAccountDto;
    }

    @Operation(summary = "Delete a bank account",
            description = "Delete an existing bank account by id.")
    @DeleteMapping("/account/{id}")
    public void deleteAccount(@PathVariable UUID id) {
        bankAccountService.deleteAccount(id);
    }
}

