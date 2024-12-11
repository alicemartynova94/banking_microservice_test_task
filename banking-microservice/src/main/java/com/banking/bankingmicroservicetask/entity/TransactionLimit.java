package com.banking.bankingmicroservicetask.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import com.banking.enums.TransactionCategory;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table("transaction_limit")
public class TransactionLimit {
    @Id
    private UUID id;

    @Column("limit_sum")
    private Double limitSum;

    @Column("transaction_category")
    private TransactionCategory transactionCategory;

    @CreationTimestamp
    @Column("limit_creation_time")
    private LocalDateTime limitCreationTime;

    @UpdateTimestamp
    @Column("limit_last_update_time")
    private LocalDateTime limitLastUpdateTime;

    @Column("limit_deleted_time")
    private LocalDateTime limitDeletedTime;

    @Column("currency_id")
    private UUID currencyShortnameId;
}
