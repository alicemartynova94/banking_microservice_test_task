package com.banking.banking_microservice_test_task.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(schema = "bank_mic", name = "currency_shortname")
@Data
public class CurrencyShortname {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;
    @Column(name = "currency_name_numb")
    private Short currencyNameNumeric;
}
