package com.banking.bankingmicroservicetask.exception_handling;

public class NoSuchBankAccountException extends RuntimeException {

    public NoSuchBankAccountException(String message) {
        super(message);
    }
}
