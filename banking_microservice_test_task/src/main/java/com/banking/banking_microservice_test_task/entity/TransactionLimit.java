package com.banking.banking_microservice_test_task.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class TransactionLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID();
    @Column(name = "limit_sum")
    private Double limitSum;
    @Column(name = "transaction_category")
    @Enumerated(EnumType.ORDINAL)
    private TransactionCategory transactionCategory;
    @CreationTimestamp
    @Column(name = "limit_creation_time")
    private LocalDateTime limitCreationTime;
    @UpdateTimestamp
    @Column(name = "limit_last_update_time")
    private LocalDateTime limitLastUpdateTime;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "currency_id")
    private CurrencyShortname currencyShortname;
}
