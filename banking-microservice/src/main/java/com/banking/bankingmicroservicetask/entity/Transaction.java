package com.banking.bankingmicroservicetask.entity;

import com.banking.enums.TransactionCategory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull
    private UUID id;
    @Column(name = "transaction_sum")
    @NotNull
    private Double transactionSum;
    @CreationTimestamp
    @Column(name = "time_of_transaction")
    private LocalDateTime timeOfTransaction;
    @Column(name = "transaction_deleted_time")
    private LocalDateTime transactionDeletedTime;
    @Column(name = "transaction_category")
    private TransactionCategory transactionCategory;
    @Column(name = "limit_exceeded")
    private Boolean limitExceeded;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "currency_id")
    @NotNull
    private CurrencyShortname currencyShortname;
    @Column(name = "bank_account_id")
    @NotNull
    private UUID bankAccountId;
}
