package com.banking.banking_microservice_test_task.exception_handling;

public class InvalidTransactionSumException extends RuntimeException {
    public InvalidTransactionSumException(String message) {
        super(message);
    }
}
