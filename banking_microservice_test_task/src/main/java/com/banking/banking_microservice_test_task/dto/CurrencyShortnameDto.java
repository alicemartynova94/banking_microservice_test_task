package com.banking.banking_microservice_test_task.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyShortnameDto {

    private UUID id;
    private Short currencyNameNumeric;
}
