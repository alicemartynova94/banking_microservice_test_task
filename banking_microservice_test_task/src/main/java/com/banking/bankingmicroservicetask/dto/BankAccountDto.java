package com.banking.bankingmicroservicetask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {

    private UUID id;
    private Integer accountNumber;
    private String accountHolder;
    private Double availableFunds;
    private CurrencyShortnameDto currencyShortname;
    private TransactionLimitDto transactionLimitGoods;
    private TransactionLimitDto transactionLimitServices;
}
