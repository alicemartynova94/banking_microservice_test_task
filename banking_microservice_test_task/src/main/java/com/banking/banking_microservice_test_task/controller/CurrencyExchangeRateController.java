package com.banking.banking_microservice_test_task.controller;

import com.banking.banking_microservice_test_task.entity.CurrencyExchangeRate;
import com.banking.banking_microservice_test_task.service.CurrencyExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CurrencyExchangeRateController {

    @Autowired
    private CurrencyExchangeRateService currencyExchangeRateService;

    @GetMapping("/rate")
    public CurrencyExchangeRate getExchangeRate(@RequestParam String baseCurrency,
                                                @RequestParam String targetCurrency) {
        String currencyPair = baseCurrency + "/" + targetCurrency;
        return currencyExchangeRateService.getExchangeRateData(currencyPair);
    }

}
