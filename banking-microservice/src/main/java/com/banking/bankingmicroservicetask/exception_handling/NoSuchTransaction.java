package com.banking.bankingmicroservicetask.exception_handling;

public class NoSuchTransaction extends RuntimeException{

    public NoSuchTransaction(String message) {
        super(message);
    }
    public NoSuchTransaction() {
        super("There is no transaction with such id");
    }

}
