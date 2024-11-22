package com.banking.bankingmicroservicetask.entity;

import com.banking.enums.TransactionCategory;
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
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private UUID id = UUID.randomUUID();
    @Column(name = "limit_sum")
    private Double limitSum;
    @Column(name = "transaction_category")
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private TransactionCategory transactionCategory;
    @CreationTimestamp
    @Column(name = "limit_creation_time")
    private LocalDateTime limitCreationTime;
    @UpdateTimestamp
    @Column(name = "limit_last_update_time")
    private LocalDateTime limitLastUpdateTime;
    @Column(name = "limit_deleted_time")
    private LocalDateTime limitDeletedTime;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "currency_id")
    @NotNull
    private CurrencyShortname currencyShortname;
}
