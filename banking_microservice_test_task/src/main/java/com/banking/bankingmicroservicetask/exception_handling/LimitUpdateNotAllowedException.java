package com.banking.bankingmicroservicetask.exception_handling;

public class LimitUpdateNotAllowedException extends RuntimeException {
    public LimitUpdateNotAllowedException(String message) {
        super(message);
    }
}
