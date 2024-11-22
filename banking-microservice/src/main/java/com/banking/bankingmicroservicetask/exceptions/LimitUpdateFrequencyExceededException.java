package com.banking.bankingmicroservicetask.exceptions;

public class LimitUpdateFrequencyExceededException extends RuntimeException {
    public LimitUpdateFrequencyExceededException(String message) {
        super(message);
    }

    public LimitUpdateFrequencyExceededException() {
        super("You've already created limit this month.");
    }
}
