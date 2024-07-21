package com.banking.banking_microservice_test_task.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyExchangeRate {

    private String symbol;

    private Double close;

    @Column(name = "previous_close")
    private Double previousClose;

}
