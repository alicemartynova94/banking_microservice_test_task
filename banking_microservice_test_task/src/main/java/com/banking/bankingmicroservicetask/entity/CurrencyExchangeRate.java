package com.banking.bankingmicroservicetask.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyExchangeRate {

    private String symbol;

    private Double close;

    @Column(name = "previous_close")
    private Double previousClose;

}
