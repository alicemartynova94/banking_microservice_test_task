package com.banking.banking_microservice_test_task.exception_handling;

public class NoSuchBankAccountException extends RuntimeException {

    public NoSuchBankAccountException(String message) {
        super(message);
    }
}
