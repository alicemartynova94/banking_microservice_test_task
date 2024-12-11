package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.dao.BankAccountRepository;
import com.banking.bankingmicroservicetask.dao.TransactionRepository;
import com.banking.bankingmicroservicetask.entity.BankAccount;
import com.banking.bankingmicroservicetask.entity.Transaction;
import com.banking.bankingmicroservicetask.exceptions.InvalidTransactionSumException;
import com.banking.bankingmicroservicetask.exceptions.NoSuchBankAccountException;
import com.banking.bankingmicroservicetask.exceptions.NoSuchTransaction;
import com.banking.bankingmicroservicetask.mappers.TransactionMapper;
import com.banking.dto.TransactionDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository transactionRepository;
  private final BankAccountRepository bankAccountRepository;
  private final TransactionalOperator transactionalOperator;

  @Autowired
  private TransactionMapper transactionMapper;

  @Override
  public Mono<Void> saveTransaction(TransactionDto transactionDto) {
    Double transactionDtoSum = transactionDto.getTransactionSum();

    if (transactionDtoSum <= 0) {
      throw new InvalidTransactionSumException();
    }

    BankAccount bankAccount = bankAccountRepository.findById(transactionDto.getBankAccountId())
        .switchIfEmpty(Mono.error(new NoSuchBankAccountException()))
        .block();
    Transaction transaction = transactionMapper.TransactionDtoToTransaction(transactionDto);

    TransactionCategoryStrategy categoryStrategy = TransactionCategoryFactory
        .getTransactionCategoryStrategy(transactionDto.getTransactionCategory());
    categoryStrategy.saveTransaction(bankAccount, transaction, transactionDtoSum);

    return saveTransactionAndBankAccount(transaction, bankAccount);
  }

  //    @Transactional
  public Mono<Void> saveTransactionAndBankAccount(Transaction transaction, BankAccount bankAccount) {
    return transactionalOperator.transactional(
        Mono.zip(
            transactionRepository.save(transaction),
            bankAccountRepository.save(bankAccount))).then();
  }

  @Override
  public Mono<TransactionDto> getTransaction(UUID id) {
    log.debug("Fetching transaction with id: {}", id);
    return transactionRepository.findByIdAndTransactionDeletedTimeIsNull(id)
        .switchIfEmpty(Mono.error(NoSuchTransaction::new))
        .map(transactionMapper::transactionToTransactionDto);
  }

  @Override
  public Flux<Transaction> getExceededLimitTransactions(UUID accountId) {
    log.debug("Fetching all transactions that exceeded limit with bank id: {}", accountId);

    //    if (transactionsList.isEmpty()) {
//      log.warn("List is empty.");
//    }

    return transactionRepository.findAllByBankAccountIdAndLimitExceededIsTrue(accountId)
        .switchIfEmpty(Mono.error(new NoSuchTransaction("List is empty"))).;
  }

  @Override
  public Mono<Void> deleteTransaction(UUID id) {
    return transactionRepository.findByIdAndTransactionDeletedTimeIsNull(id)
        .switchIfEmpty(Mono.error(new NoSuchTransaction()))
        .flatMap(transaction -> {
          transaction.setTransactionDeletedTime(LocalDateTime.now());
          return transactionRepository.save(transaction).then();
        })
        .doOnSuccess((v) -> log.debug("Transaction with id: {} was deleted", id));
  }
}
