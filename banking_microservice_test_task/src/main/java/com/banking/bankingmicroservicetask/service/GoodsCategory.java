package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.entity.BankAccount;
import com.banking.bankingmicroservicetask.entity.Transaction;

public class GoodsCategory implements TransactionCategoryStrategy {

    @Override
    public void saveTransaction(BankAccount bankAccount, Transaction transaction, Double transactionDtoSum) {
        Double limitGoods = bankAccount.getLimitGoods();
        Double accountAvailableFunds = bankAccount.getAvailableFunds();

        if (transactionDtoSum <= limitGoods) {
            limitGoods -= transactionDtoSum;
            bankAccount.setLimitGoods(limitGoods);
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
