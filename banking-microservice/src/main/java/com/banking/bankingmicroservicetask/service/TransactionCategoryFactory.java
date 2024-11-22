package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.entity.TransactionCategory;
import com.banking.bankingmicroservicetask.exception_handling.InvalidTransactionTypeException;

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
