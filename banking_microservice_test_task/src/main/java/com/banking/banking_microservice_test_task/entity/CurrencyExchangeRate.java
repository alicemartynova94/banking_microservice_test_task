package com.banking.banking_microservice_test_task.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CurrencyExchangeRate {

    private String symbol;

    private Double rate;

    private Integer timestamp;

}
