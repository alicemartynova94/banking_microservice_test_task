package com.banking.banking_microservice_test_task.service;

import com.banking.banking_microservice_test_task.entity.CurrencyExchangeRate;
import com.banking.banking_microservice_test_task.properties.CurrencyExchangeRateServiceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CurrencyExchangeRateServiceImpl implements CurrencyExchangeRateService {

    @Autowired
    private CurrencyExchangeRateServiceProperties properties;

    @Override
    public CurrencyExchangeRate getExchangeRateData(String currencyName) {
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(properties.getBaseUrl())
                .queryParam("symbol", currencyName)
                .queryParam("interval", properties.getInterval())
                .queryParam("apikey", properties.getApiKey())
                .build();

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(builder.toUriString(), CurrencyExchangeRate.class);
    }

    @Scheduled(cron = "#{@currencyExchangeRateServiceProperties.cron}")
    public void getExchangeRateDataScheduler(){
        getExchangeRateData("KZT/USD");
        getExchangeRateData("RUB/USD");
    }
}
