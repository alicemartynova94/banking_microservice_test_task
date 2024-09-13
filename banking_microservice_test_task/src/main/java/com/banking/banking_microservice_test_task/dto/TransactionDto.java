package com.banking.banking_microservice_test_task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private UUID id;
    private Double transactionSum;
    private LocalDateTime timeOfTransaction;
    private String expenseCategory;
    private Boolean transactionStatus;
    private Boolean limitExceeded;
}
