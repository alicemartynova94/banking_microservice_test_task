package com.banking.bankingmicroservicetask.dto;

import com.banking.bankingmicroservicetask.entity.TransactionCategory;
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
    private Double transactionSum;
    private TransactionCategory transactionCategory;
    private CurrencyShortnameDto currencyShortname;
}
