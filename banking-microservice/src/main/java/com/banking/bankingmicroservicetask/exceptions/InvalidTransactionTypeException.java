package com.banking.bankingmicroservicetask.exceptions;

public class InvalidTransactionTypeException extends RuntimeException {
    public InvalidTransactionTypeException(String message) {
        super(message);
    }

    public InvalidTransactionTypeException() {
        super("There are only two transaction types available: GOODS and SERVICES.");
    }
}
