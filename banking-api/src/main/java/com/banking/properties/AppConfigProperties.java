package com.banking.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "app.config")
public class AppConfigProperties {
    @NotBlank(message = "Root url cannot be blank.")
    private String baseUrl;
}
