package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.dao.BankAccountRepository;
import com.banking.bankingmicroservicetask.dao.TransactionRepository;
import com.banking.bankingmicroservicetask.entity.BankAccount;
import java.util.function.BiConsumer;
import org.springframework.stereotype.Component;

@Component("SERVICES")
public class ServicesCategory extends TransactionCategoryStrategy {

  public ServicesCategory(TransactionRepository transactionRepository,
      BankAccountRepository bankAccountRepository) {
    super(transactionRepository, bankAccountRepository);
  }

  @Override
  protected Double getLimit(BankAccount bankAccount) {
    return bankAccount.getLimitServices();
  }

  @Override
  protected BiConsumer<BankAccount, Double> getLimitSetter() {
    return BankAccount::setLimitServices;
  }
}
