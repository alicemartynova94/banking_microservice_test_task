package com.banking.bankingmicroservicetask.exceptions;

public class InvalidTransactionSumException extends RuntimeException {
    public InvalidTransactionSumException(String message) {
        super(message);
    }
    public InvalidTransactionSumException() {
        super("Transaction sum cannot be a negative number or null.");
    }

}
