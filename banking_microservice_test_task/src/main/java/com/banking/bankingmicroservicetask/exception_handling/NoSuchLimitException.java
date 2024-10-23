package com.banking.bankingmicroservicetask.exception_handling;

public class NoSuchLimitException extends RuntimeException {
    public NoSuchLimitException(String message) {
        super(message);
    }
}
