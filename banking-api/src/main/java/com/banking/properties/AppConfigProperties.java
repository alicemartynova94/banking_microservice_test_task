package com.banking.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Data
@Validated
@Component
@ConfigurationProperties(prefix = "app.config")
public class AppConfigProperties {
    @NotBlank(message = "Root url cannot be blank.")
    private String baseUrl;
    @NotNull(message = "Connect timeout cannot be blank.")
    private Duration connectTimeout;
    @NotNull(message = "Read timeout cannot be blank.")
    private Duration readTimeout;
}
