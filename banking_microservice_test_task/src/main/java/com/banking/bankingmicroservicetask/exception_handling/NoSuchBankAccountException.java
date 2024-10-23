package com.banking.bankingmicroservicetask.exception_handling;

public class NoSuchBankAccountException extends RuntimeException {

    public NoSuchBankAccountException(String message) {
        super(message);
    }
    public NoSuchBankAccountException() {
        super("There is no bank account with such id");
    }
}
