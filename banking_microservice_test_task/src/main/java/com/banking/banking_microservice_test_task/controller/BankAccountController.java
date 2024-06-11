package com.banking.banking_microservice_test_task.controller;

import com.banking.banking_microservice_test_task.entity.BankAccount;
import com.banking.banking_microservice_test_task.exception_handling.BankAccountIncorrectData;
import com.banking.banking_microservice_test_task.exception_handling.NoSuchBankAccountException;
import com.banking.banking_microservice_test_task.service.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class BankAccountController {
    private static final Logger logger = LoggerFactory.getLogger(BankAccountController.class);
    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/account/{id}")
    public BankAccount getAccount(@PathVariable UUID id) {
        logger.info("Received request to get bank account with id: {}", id);
        BankAccount bankAccount = bankAccountService.getAccount(id);

        if (bankAccount == null) {
            throw new NoSuchBankAccountException("There is no bank account with id " + id + " in database.");
        }

        logger.info("Responding with bank account details for id: {}", id);
        return bankAccount;
    }

    @PostMapping("/accounts")
    public BankAccount addNewAccount(@RequestBody BankAccount bankAccount) {
        logger.info("Received request to save bank account: {}", bankAccount);

        bankAccountService.saveAccount(bankAccount);

        logger.info("Bank account saved successfully with id: {}", bankAccount.getId());
        return bankAccount;
    }

    @DeleteMapping("/account/{id}")
    public void deleteAccount(@PathVariable UUID id) {
        logger.info("Received request to delete bank account with id: {}", id);

        BankAccount bankAccount = bankAccountService.getAccount(id);
        if (bankAccount == null) {
            throw new NoSuchBankAccountException("There is no bank account with id " + id + " in database, so it cannot be deleted.");
        }

        logger.info("Bank account with id: {} deleted successfully", id);
        bankAccountService.deleteAccount(id);
    }
}

