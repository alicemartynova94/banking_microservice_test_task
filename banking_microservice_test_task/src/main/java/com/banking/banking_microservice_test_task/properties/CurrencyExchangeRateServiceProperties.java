package com.banking.banking_microservice_test_task.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "currency.service")
public class CurrencyExchangeRateServiceProperties {
    private String apiKey;
    private String baseUrl;
    private String interval;
}
