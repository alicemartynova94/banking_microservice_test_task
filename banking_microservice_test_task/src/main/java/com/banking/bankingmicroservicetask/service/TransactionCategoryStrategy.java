package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.entity.BankAccount;
import com.banking.bankingmicroservicetask.entity.Transaction;

public interface TransactionCategoryStrategy {

    void saveTransaction(BankAccount bankAccount, Transaction transaction, Double transactionDtoSum);
}
