//package com.banking.bankingmicroservicetask.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.Column;
//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.NotNull;
//import lombok.Data;
//
//
//@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class CurrencyExchangeRate {
//    @NotNull
//    @Max(10)
//    private String symbol;
//
//    private Double close;
//
//    @Column(name = "previous_close")
//    private Double previousClose;
//
//}
