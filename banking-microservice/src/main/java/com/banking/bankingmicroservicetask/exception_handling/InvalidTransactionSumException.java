package com.banking.bankingmicroservicetask.exception_handling;

public class InvalidTransactionSumException extends RuntimeException {
    public InvalidTransactionSumException(String message) {
        super(message);
    }
    public InvalidTransactionSumException() {
        super("Transaction sum cannot be a negative number or null.");
    }

}
