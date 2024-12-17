package com.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {

    private UUID id;
    private Integer accountNumber;
    private String accountHolder;
    private Double availableFunds;
    private UUID currencyShortnameId;
    private UUID transactionLimitGoodsId;
    private UUID transactionLimitServicesId;
}
