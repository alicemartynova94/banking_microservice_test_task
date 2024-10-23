package com.banking.banking_microservice_test_task.dto;

import com.banking.banking_microservice_test_task.entity.TransactionCategory;
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
