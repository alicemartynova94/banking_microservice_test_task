package com.banking.bankingmicroservicetask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BankingMicroserviceTestTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingMicroserviceTestTaskApplication.class, args);
    }


}
