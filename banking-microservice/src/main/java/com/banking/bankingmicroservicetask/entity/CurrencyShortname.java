package com.banking.bankingmicroservicetask.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class CurrencyShortname {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull
    private UUID id;
    @Column(name = "currency_name_numb")
    private Short currencyNameNumeric;
}
