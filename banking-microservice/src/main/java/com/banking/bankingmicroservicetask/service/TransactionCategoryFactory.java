package com.banking.bankingmicroservicetask.service;


import com.banking.bankingmicroservicetask.exceptions.InvalidTransactionTypeException;
import com.banking.enums.TransactionCategory;

public class TransactionCategoryFactory {

    public static TransactionCategoryStrategy getTransactionCategoryStrategy(TransactionCategory transactionCategory) {
        switch (transactionCategory) {
            case GOODS:
                return new GoodsCategory();
            case SERVICES:
                return new ServicesCategory();
            default:
                throw new InvalidTransactionTypeException();
        }
    }

}
