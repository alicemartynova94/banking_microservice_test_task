package com.banking.banking_microservice_test_task.exception_handling;

public class NoSuchLimitException extends RuntimeException {
    public NoSuchLimitException(String message) {
        super(message);
    }
}
