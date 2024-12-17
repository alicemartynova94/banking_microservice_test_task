package com.banking.bankingmicroservicetask.entity;

import com.banking.enums.TransactionCategory;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("transaction")
public class Transaction {

    @Id
    @NotNull
    private UUID id;
    @Column("transaction_sum")
    @NotNull
    private Double transactionSum;
    @CreationTimestamp
    @Column("time_of_transaction")
    private LocalDateTime timeOfTransaction;
    @Column("transaction_deleted_time")
    private LocalDateTime transactionDeletedTime;
    @Column("transaction_category")
    private TransactionCategory transactionCategory;
    @Column("limit_exceeded")
    private Boolean limitExceeded;
    @Column("currency_id")
    @NotNull
    private UUID currencyShortnameId;
    @Column("bank_account_id")
    @NotNull
    private UUID bankAccountId;
}
