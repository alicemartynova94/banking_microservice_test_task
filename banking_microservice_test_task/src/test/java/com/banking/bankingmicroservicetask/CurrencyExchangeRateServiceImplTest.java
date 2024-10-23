package com.banking.bankingmicroservicetask;

import com.banking.bankingmicroservicetask.entity.CurrencyExchangeRate;
import com.banking.bankingmicroservicetask.properties.CurrencyExchangeRateServiceProperties;
import com.banking.bankingmicroservicetask.service.CurrencyExchangeRateServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class CurrencyExchangeRateServiceImplTest {
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private CurrencyExchangeRateServiceProperties properties;
    @InjectMocks
    private CurrencyExchangeRateServiceImpl exchangeRateService;

    private CurrencyExchangeRate currencyExchangeRateMock;
    private String symbol;
    private String baseUrl;
    private String apiKey;
    private String interval;

    private Double close;
    private Double previousClose;

    @BeforeEach
    void init() {
        symbol = "USD/KZT";
        baseUrl = "https://api.twelvedata.com/quote";
        apiKey = "677ba07b6c475fa2a";
        interval = "30min";

        close = 474.82501;
        previousClose = null;

        currencyExchangeRateMock = new CurrencyExchangeRate();
        currencyExchangeRateMock.setSymbol(symbol);
        currencyExchangeRateMock.setClose(close);
        currencyExchangeRateMock.setPreviousClose(previousClose);
    }

    @Test
    public void getExchangeRateDataExpectValidExchangeRate() {
        given(properties.getBaseUrl()).willReturn(baseUrl);
        given(properties.getInterval()).willReturn(interval);
        given(properties.getApiKey()).willReturn(apiKey);

        given(restTemplate.getForObject(anyString(), any())).willReturn(currencyExchangeRateMock);

        CurrencyExchangeRate result = exchangeRateService.getExchangeRateData(symbol);

        Assertions.assertEquals(symbol, result.getSymbol());
        Assertions.assertEquals(close, result.getClose());
        Assertions.assertEquals(previousClose, result.getPreviousClose());
    }
}
