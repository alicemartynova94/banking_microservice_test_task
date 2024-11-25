package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.entity.CurrencyExchangeRate;
import com.banking.bankingmicroservicetask.properties.CurrencyExchangeRateServiceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeRateServiceImpl implements CurrencyExchangeRateService {

    private final RestTemplate restTemplate;
    private final CurrencyExchangeRateServiceProperties properties;

    @Override
    public CurrencyExchangeRate getExchangeRateData(String currencyName) {
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(properties.getBaseUrl())
                .queryParam("symbol", currencyName)
                .queryParam("interval", properties.getInterval())
                .queryParam("apikey", properties.getApiKey())
                .build();

        return restTemplate.getForObject(builder.toUriString(), CurrencyExchangeRate.class);
    }

    @Scheduled(cron = "#{@currencyExchangeRateServiceProperties.cron}")
    public void getExchangeRateDataScheduler() {
        getExchangeRateData("KZT/USD");
        getExchangeRateData("RUB/USD");
    }
}
