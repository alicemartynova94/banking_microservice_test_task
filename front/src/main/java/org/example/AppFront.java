package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@Slf4j
@SpringBootApplication
@EnableReactiveFeignClients(basePackages = "com.banking.api")
@ComponentScan(basePackages = "com.banking.api")
public class AppFront {

    public static final String SERVICE_NAME = "banking-front";

    public static void main(String[] args) {
        log.info(String.format("Starting %s application", SERVICE_NAME));
        SpringApplication.run(AppFront.class);
    }
}