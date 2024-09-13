package com.banking.banking_microservice_test_task.controller;

import com.banking.banking_microservice_test_task.dto.TransactionDto;
import com.banking.banking_microservice_test_task.service.TransactionService;
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

    @GetMapping("/transactions/account/{id}")
    public List<TransactionDto> getListOfTransactions() {
        return transactionService.getListOfTransactions();
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
