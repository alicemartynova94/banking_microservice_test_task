package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.entity.BankAccount;
import com.banking.bankingmicroservicetask.entity.Transaction;

public class ServicesCategory implements TransactionCategoryStrategy {

    @Override
    public void saveTransaction(BankAccount bankAccount, Transaction transaction, Double transactionDtoSum) {
        Double limitServices = bankAccount.getLimitServices();
        Double accountAvailableFunds = bankAccount.getAvailableFunds();

        if (transactionDtoSum <= limitServices) {
            limitServices -= transactionDtoSum;
            bankAccount.setLimitGoods(limitServices);
            bankAccount.setAvailableFunds(accountAvailableFunds - transactionDtoSum);
            bankAccount.getTransaction().add(transaction);
            transaction.setLimitExceeded(false);
        } else {
            transaction.setLimitExceeded(true);
            System.out.println("This transaction will be saved to your transactions' history but " +
                    "won't be performed due to your limit policy");
        }
    }

}
