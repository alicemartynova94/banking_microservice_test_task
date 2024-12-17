package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.dao.BankAccountRepository;
import com.banking.bankingmicroservicetask.dao.TransactionRepository;
import com.banking.bankingmicroservicetask.entity.BankAccount;
import java.util.function.BiConsumer;
import org.springframework.stereotype.Component;

@Component("GOODS")
public class GoodsCategory extends TransactionCategoryStrategy {

  public GoodsCategory(TransactionRepository transactionRepository, BankAccountRepository bankAccountRepository) {
    super(transactionRepository, bankAccountRepository);
  }

  @Override
  protected Double getLimit(BankAccount bankAccount) {
    return bankAccount.getLimitGoods();
  }

  @Override
  protected BiConsumer<BankAccount, Double> getLimitSetter() {
    return BankAccount::setLimitGoods;
  }
}
