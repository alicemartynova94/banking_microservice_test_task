package com.banking.banking_microservice_test_task.exception_handling;

public class LimitUpdateNotAllowedException extends RuntimeException {
    public LimitUpdateNotAllowedException(String message) {
        super(message);
    }
}
