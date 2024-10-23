package com.banking.banking_microservice_test_task.controller;

import com.banking.banking_microservice_test_task.dto.TransactionLimitDto;
import com.banking.banking_microservice_test_task.service.TransactionLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TransactionLimitController {
    @Autowired
    TransactionLimitService transactionLimitService;

    @PostMapping("/limits")
    public void saveLimit(@RequestBody TransactionLimitDto limitDto) {
        transactionLimitService.saveLimit(limitDto);
    }

    @GetMapping("/limit/{id}")
    public TransactionLimitDto getLimit(@PathVariable UUID id) {
        return transactionLimitService.getLimit(id);
    }

    @PutMapping("/limits")
    public void updateLimit(@PathVariable UUID id, @RequestBody TransactionLimitDto transactionLimitDto) {
        transactionLimitService.updateLimit(id, transactionLimitDto);
    }


    @DeleteMapping("/limit/{id}")
    public void deleteLimit(@PathVariable UUID id) {
        transactionLimitService.deleteLimit(id);
    }

}
