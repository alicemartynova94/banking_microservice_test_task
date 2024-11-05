package com.banking.bankingmicroservicetask.exceptions;

public class NoSuchLimitException extends RuntimeException {
    public NoSuchLimitException(String message) {
        super(message);
    }

    public NoSuchLimitException() {
        super("Limit with this id is not found.");
    }

}
