package com.banking.banking_microservice_test_task.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "currency.service")
public class CurrencyExchangeRateServiceProperties {
    @NotBlank(message = "Api key field cannot be Blank!")
    private String apiKey;
    @NotBlank(message = "Base url key field cannot be Blank!")
    private String baseUrl;
    @NotBlank(message = "Interval key field cannot be Blank!")
    private String interval;
}
