package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.dao.BankAccountRepository;
import com.banking.bankingmicroservicetask.dao.TransactionRepository;
import com.banking.bankingmicroservicetask.entity.Transaction;
import com.banking.bankingmicroservicetask.exceptions.InvalidTransactionSumException;
import com.banking.bankingmicroservicetask.exceptions.InvalidTransactionTypeException;
import com.banking.bankingmicroservicetask.exceptions.NoSuchBankAccountException;
import com.banking.bankingmicroservicetask.exceptions.NoSuchTransaction;
import com.banking.bankingmicroservicetask.mappers.TransactionMapper;
import com.banking.dto.TransactionDto;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
  private final Map<String, TransactionCategoryStrategy> strategies;
  private final TransactionMapper transactionMapper;

  @Override
  public Mono<Void> saveTransaction(TransactionDto transactionDto) {
    return Mono.defer(() -> {
      Double transactionDtoSum = transactionDto.getTransactionSum();
      if (transactionDtoSum == null || transactionDtoSum <= 0) {
        return Mono.error(new InvalidTransactionSumException());
      }
      return bankAccountRepository.findByIdAndBankAccountDeletedTimeIsNull(transactionDto.getBankAccountId())
          .switchIfEmpty(Mono.error(new NoSuchBankAccountException()))
          .flatMap(bankAccount -> {
            Transaction transaction = transactionMapper.TransactionDtoToTransaction(transactionDto);
            TransactionCategoryStrategy categoryStrategy = strategies.get(
                transactionDto.getTransactionCategory().name());
            if (categoryStrategy == null) {
              return Mono.error(InvalidTransactionTypeException::new);
            }
            return categoryStrategy.saveTransaction(bankAccount, transaction, transactionDtoSum);
          })
          .as(transactionalOperator::transactional);
    });
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
    return transactionRepository.findAllByBankAccountIdAndLimitExceededIsTrue(accountId)
        .switchIfEmpty(
            Mono.fromRunnable(() -> log.debug("No exceeded limit transactions found for accountId: {}", accountId))
        );
  }

  @Override
  public Mono<Void> deleteTransaction(UUID id) {
    return transactionRepository.findByIdAndTransactionDeletedTimeIsNull(id)
        .switchIfEmpty(Mono.error(new NoSuchTransaction()))
        .flatMap(transaction -> {
          transaction.setTransactionDeletedTime(LocalDateTime.now());
          return transactionRepository.save(transaction);
        })
        .doOnSuccess(v -> log.debug("Transaction with id: {} was deleted", id))
        .doOnError(error -> log.error("Error occurred while deleting transaction with id: {}", id, error))
        .then();
  }
}
