package com.banking.banking_microservice_test_task.service;

import com.banking.banking_microservice_test_task.entity.CurrencyExchangeRate;

public interface CurrencyExchangeRateService {

    public CurrencyExchangeRate getExchangeRateData(String currencyName);
}
