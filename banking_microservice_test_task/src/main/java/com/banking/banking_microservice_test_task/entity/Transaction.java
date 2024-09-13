package com.banking.banking_microservice_test_task.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "transaction_sum")
    private Double transactionSum;
    @CreationTimestamp
    @Column(name = "time_of_transaction")
    private LocalDateTime timeOfTransaction;
    @Column(name = "expense_category")
    private String expenseCategory;
    @Column(name = "transaction_status")
    private Boolean transactionStatus;
    @Column(name = "limit_exceeded")
    private Boolean limitExceeded;
}
