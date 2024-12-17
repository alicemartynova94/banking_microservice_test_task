package com.banking.bankingmicroservicetask.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("currency_shortname")
public class CurrencyShortname {
    @Id
    @NotNull
    private UUID id;
    @Column("currency_name_numeric")
    private Short currencyNameNumeric;
}
