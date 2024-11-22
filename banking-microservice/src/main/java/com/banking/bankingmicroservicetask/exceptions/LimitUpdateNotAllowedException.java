package com.banking.bankingmicroservicetask.exceptions;

public class LimitUpdateNotAllowedException extends RuntimeException {
    public LimitUpdateNotAllowedException(String message) {
        super(message);
    }

    public LimitUpdateNotAllowedException() {
        super("Limit updates are only allowed on the 15th of each month.");
    }

}
