package com.banking.bankingmicroservicetask.exceptions;

public class NoSuchTransaction extends RuntimeException{

    public NoSuchTransaction(String message) {
        super(message);
    }
    public NoSuchTransaction() {
        super("There is no transaction with such id");
    }

}
