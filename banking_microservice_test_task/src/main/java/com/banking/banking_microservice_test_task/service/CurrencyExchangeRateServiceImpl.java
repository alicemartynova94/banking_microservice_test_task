package com.banking.banking_microservice_test_task.service;

import com.banking.banking_microservice_test_task.entity.CurrencyExchangeRate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyExchangeRateServiceImpl implements CurrencyExchangeRateService {

    private static final String API_KEY = "e1677ba07b6c475fa2a42e7402131fe2";
    private static final String BASE_URL = "https://api.twelvedata.com/exchange_rate";

    @Override
    public CurrencyExchangeRate getExchangeRateData(String currencyName) {
        String resource_url = String.format("%s?symbol=%s&apikey=%s", BASE_URL, currencyName, API_KEY);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(resource_url, CurrencyExchangeRate.class);
    }
}
