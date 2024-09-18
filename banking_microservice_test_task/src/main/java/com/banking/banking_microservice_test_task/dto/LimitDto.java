package com.banking.banking_microservice_test_task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LimitDto {

    private UUID id;
    private Double limitSum;
    private LocalDateTime limitLastUpdateTime;
    private CurrencyShortnameDto currencyShortname;
}