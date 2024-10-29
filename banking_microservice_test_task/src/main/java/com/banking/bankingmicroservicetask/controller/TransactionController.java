package com.banking.bankingmicroservicetask.controller;

import com.banking.bankingmicroservicetask.entity.Transaction;
import com.banking.bankingmicroservicetask.service.TransactionService;
import com.banking.bankingmicroservicetask.dto.TransactionDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "get", description = "Get methods of Transaction API.")
    @GetMapping("/transaction/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid transaction data"),
            @ApiResponse(responseCode = "404", description = "Bank account for this transaction is not found")
    })
    public TransactionDto getTransaction(@PathVariable UUID id) {
        return transactionService.getTransaction(id);
    }

    @Tag(name = "get", description = "Get methods of Transaction API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions that exceed limit or an empty list retrieved successfully",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Transaction.class))))
    })
    @GetMapping("/transactions/{accountId}")
    public List<Transaction> getExceededLimitTransactions(@PathVariable UUID accountId) {
        return transactionService.getExceededLimitTransactions(accountId);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid transaction data")
    })
    @PostMapping("/transactions")
    public TransactionDto addNewTransaction(@RequestBody TransactionDto transactionDto) {
        transactionService.saveTransaction(transactionDto);
        return transactionDto;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @DeleteMapping("/transaction/{id}")
    public void deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransaction(id);
    }

}
