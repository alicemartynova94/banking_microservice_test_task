package com.banking.dto;

import com.banking.enums.TransactionCategory;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private UUID id;
    private UUID bankAccountId;
    @NotNull
    private Double transactionSum;
    private TransactionCategory transactionCategory;
    private CurrencyShortnameDto currencyShortname;
}
