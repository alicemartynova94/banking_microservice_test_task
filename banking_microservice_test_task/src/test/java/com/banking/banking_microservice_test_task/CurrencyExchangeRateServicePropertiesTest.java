package com.banking.banking_microservice_test_task;

import com.banking.banking_microservice_test_task.properties.CurrencyExchangeRateServiceProperties;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyExchangeRateServicePropertiesTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final List<String> required = Arrays.asList("e1677ba07b6c475fa2a42e7402131fe2", "https://api.twelvedata.com/quote", "30min");

    @Test
    public void givenValidPropertiesExpectNoConstraintViolations() {
        CurrencyExchangeRateServiceProperties properties = new CurrencyExchangeRateServiceProperties();

        properties.setApiKey("e1677ba07b6c475fa2a42e7402131fe2");
        properties.setBaseUrl("https://api.twelvedata.com/quote");
        properties.setInterval("30");

        var errors = validator.validate(properties);

        Assertions.assertEquals(0, errors.size());
    }

    @Test
    public void givenInvalidPropertiesExpectConstraintViolations() {
        CurrencyExchangeRateServiceProperties properties = new CurrencyExchangeRateServiceProperties();

        properties.setApiKey("");
        properties.setBaseUrl("");
        properties.setInterval("");

        var errors = validator.validate(properties);
        Assertions.assertEquals(3, errors.size());


        for (ConstraintViolation<CurrencyExchangeRateServiceProperties> violation : errors) {
            if (violation.getPropertyPath().toString().equals("apiKey")) {
                assertThat(violation.getMessage()).isEqualTo("Api key field cannot be Blank!");
            } else if (violation.getPropertyPath().toString().equals("baseUrl")) {
                assertThat(violation.getMessage()).isEqualTo("Base url key field cannot be Blank!");
            } else if (violation.getPropertyPath().toString().equals("interval")) {
                assertThat(violation.getMessage()).isEqualTo("Interval key field cannot be Blank!");
            }
        }
    }
}
