package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = "com.banking")
public class AppFront {

    public static final String SERVICE_NAME = "banking-front";

    public static void main(String[] args) {
        log.info(String.format("Starting %s application", SERVICE_NAME));
        SpringApplication.run(AppFront.class);
    }
}