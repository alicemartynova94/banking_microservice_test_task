package com.banking.bankingmicroservicetask.exception_handling;

public class LimitUpdateFrequencyExceededException extends RuntimeException {
    public LimitUpdateFrequencyExceededException(String message) {
        super(message);
    }
}