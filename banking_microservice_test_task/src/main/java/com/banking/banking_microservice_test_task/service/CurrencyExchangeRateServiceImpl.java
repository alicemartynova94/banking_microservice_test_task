package com.banking.banking_microservice_test_task.service;

import com.banking.banking_microservice_test_task.entity.CurrencyExchangeRate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CurrencyExchangeRateServiceImpl implements CurrencyExchangeRateService {

    @Value("${apikey}")
    private String apiKey;
    @Value("${baseurl}")
    private String baseUrl;

    @Override
    public CurrencyExchangeRate getExchangeRateData(String currencyName) {
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("symbol", currencyName)
                .queryParam("interval", "30min")
                .queryParam("apikey", apiKey)
                .build();

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(builder.toUriString(), CurrencyExchangeRate.class);
    }

    @Scheduled(cron = "00****")
    public void getExchangeRateDataScheduler(){
        getExchangeRateData("KZT/USD");
        getExchangeRateData("RUB/USD");
    }
}
