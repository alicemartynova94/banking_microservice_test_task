package com.banking.bankingmicroservicetask.exception_handling;

public class InvalidTransactionSumException extends RuntimeException {
    public InvalidTransactionSumException(String message) {
        super(message);
    }
}
