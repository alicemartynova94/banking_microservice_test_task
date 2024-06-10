package com.banking.banking_microservice_test_task.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Entity
@Table(schema = "bank_mic", name = "bank_account")
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
    @PrimaryKeyJoinColumn(name = "currency_shortname")
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id")
    private CurrencyShortname currencyShortname;
}