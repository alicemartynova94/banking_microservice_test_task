package com.banking.banking_microservice_test_task.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

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
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "currency_shortname_id")
    private CurrencyShortname currencyShortname;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "limit_goods_id")
    private TransactionLimit transactionLimitGoods;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "limit_services_id")
    private TransactionLimit transactionLimitServices;
}
