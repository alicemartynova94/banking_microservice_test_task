package com.banking.bankingmicroservicetask.controller;

import com.banking.bankingmicroservicetask.dto.TransactionLimitDto;
import com.banking.bankingmicroservicetask.service.TransactionLimitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@Tag(name = "Transaction Limit API", description = "Operations related to transaction limit.")
public class TransactionLimitController {
    @Autowired
    TransactionLimitService transactionLimitService;

    @Operation(summary = "Save a limit",
            description = "Save a new limit into database.")
    @PostMapping("/limits")
    public void saveLimit(@RequestBody TransactionLimitDto limitDto) {
        transactionLimitService.saveLimit(limitDto);
    }

    @Operation(summary = "Get a transaction limit",
            description = "Get an existing transaction limit by id.")
    @GetMapping("/limit/{id}")
    public TransactionLimitDto getLimit(@PathVariable UUID id) {
        return transactionLimitService.getLimit(id);
    }

    @Operation(summary = "Update a limit",
            description = "Update an existing limit.")
    @PutMapping("/limits")
    public void updateLimit(@PathVariable UUID id, @RequestBody TransactionLimitDto transactionLimitDto) {
        transactionLimitService.updateLimit(id, transactionLimitDto);
    }

    @Operation(summary = "Delete a limit",
            description = "Delete an existing limit by id.")
    @DeleteMapping("/limit/{id}")
    public void deleteLimit(@PathVariable UUID id) {
        transactionLimitService.deleteLimit(id);
    }

}
