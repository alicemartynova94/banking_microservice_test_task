package com.banking.banking_microservice_test_task.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "account.service")
public class BankAccountServiceProperties {
    @NotBlank(message = "Cron cannot be Blank!")
    private String cron;
}
