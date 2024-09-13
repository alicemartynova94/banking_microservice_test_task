package com.banking.banking_microservice_test_task.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "account_id")
    private BankAccount account;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "currency_id")
    private CurrencyShortname currencyShortname;
}
