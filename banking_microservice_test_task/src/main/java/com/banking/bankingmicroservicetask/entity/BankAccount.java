package com.banking.bankingmicroservicetask.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Data
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "account_number")
    private Integer accountNumber;
    @Column(name = "account_holder")
    private String accountHolder;
    @Column(name = "available_funds")
    private Double availableFunds;
    @Column(name = "account_deleted_time")
    private LocalDateTime bankAccountDeletedTime;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "currency_shortname_id")
    private CurrencyShortname currencyShortname;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "limit_goods_id")
    private TransactionLimit transactionLimitGoods;
    @Column(name = "limit_goods")
    private Double limitGoods;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "limit_services_id")
    private TransactionLimit transactionLimitServices;
    @Column(name = "limit_services")
    private Double limitServices;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "transaction_id")
    private List<Transaction> transaction;
}
