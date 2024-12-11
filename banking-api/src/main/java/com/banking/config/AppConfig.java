package com.banking.config;

import com.banking.properties.AppConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(AppConfigProperties.class)
@RequiredArgsConstructor
public class AppConfig {

    private final AppConfigProperties appConfigProperties;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .rootUri(appConfigProperties.getBaseUrl())
                .setConnectTimeout(appConfigProperties.getConnectTimeout())
                .setReadTimeout(appConfigProperties.getReadTimeout())
                .build();
    }
}
