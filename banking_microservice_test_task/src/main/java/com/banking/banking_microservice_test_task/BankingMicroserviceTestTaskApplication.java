package com.banking.banking_microservice_test_task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingMicroserviceTestTaskApplication {
    private static final Logger logger = LoggerFactory.getLogger(BankingMicroserviceTestTaskApplication.class);

    public static void main(String[] args) {
        logger.info("Starting the application");
        SpringApplication.run(BankingMicroserviceTestTaskApplication.class, args);
        logger.info("Application started successfully");
    }

}
