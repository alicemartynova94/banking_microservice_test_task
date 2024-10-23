package com.banking.bankingmicroservicetask.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class CurrencyShortname {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "currency_name_numb")
    private Short currencyNameNumeric;
}
