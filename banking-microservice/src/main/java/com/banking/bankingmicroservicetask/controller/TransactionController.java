package com.banking.bankingmicroservicetask.controller;

import com.banking.bankingmicroservicetask.entity.Transaction;
import com.banking.bankingmicroservicetask.service.TransactionService;
import com.banking.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transaction/{id}")
    public TransactionDto getTransaction(@PathVariable UUID id) {
        return transactionService.getTransaction(id);
    }

    @GetMapping("/transactions/{accountId}")
    public List<Transaction> getExceededLimitTransactions(@PathVariable UUID accountId) {
        return transactionService.getExceededLimitTransactions(accountId);
    }

    @PostMapping("/transactions")
    public TransactionDto addNewTransaction(@RequestBody TransactionDto transactionDto) {
        transactionService.saveTransaction(transactionDto);
        return transactionDto;
    }

    @DeleteMapping("/transaction/{id}")
    public void deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransaction(id);
    }

}
