package com.banking.banking_microservice_test_task.exception_handling;

public class LimitUpdateFrequencyExceededException extends RuntimeException {
    public LimitUpdateFrequencyExceededException(String message) {
        super(message);
    }
}
