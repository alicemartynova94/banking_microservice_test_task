package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.dao.BankAccountRepository;
import com.banking.bankingmicroservicetask.dao.TransactionRepository;
import com.banking.bankingmicroservicetask.entity.BankAccount;
import com.banking.bankingmicroservicetask.entity.Transaction;
import java.util.function.BiConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public abstract class TransactionCategoryStrategy {

  protected abstract Double getLimit(BankAccount bankAccount);
  protected abstract BiConsumer<BankAccount, Double> getLimitSetter();

  private final TransactionRepository transactionRepository;
  private final BankAccountRepository bankAccountRepository;

  public Mono<Void> saveTransaction(BankAccount bankAccount, Transaction transaction, Double transactionDtoSum) {
    Double limitServices = getLimit(bankAccount);

    if (transactionDtoSum <= limitServices) {
      getLimitSetter().accept(bankAccount, transactionDtoSum);
      bankAccount.setAvailableFunds(bankAccount.getAvailableFunds() - transactionDtoSum);
      transaction.setLimitExceeded(false);
    } else {
      transaction.setLimitExceeded(true);
      log.warn("This transaction will be saved to your transactions' history but " +
          "won't be performed due to your limit policy");
    }
    return Mono.just(transaction)
        .flatMap(transactionRepository::save)
        .then(bankAccountRepository.save(bankAccount))
        .then();
  }
}
