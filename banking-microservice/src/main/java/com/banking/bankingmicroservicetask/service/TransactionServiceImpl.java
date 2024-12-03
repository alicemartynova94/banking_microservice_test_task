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
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
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

        saveTransactionAndBankAccount(transaction, bankAccount);
    }

    @Transactional
    public void saveTransactionAndBankAccount(Transaction transaction, BankAccount bankAccount) {
        transactionRepository.save(transaction);
        bankAccountRepository.save(bankAccount).block();
    }


    @Override
    public TransactionDto getTransaction(UUID id) {
        log.debug("Fetching transaction with id: {}", id);

        Transaction transaction = transactionRepository.findByIdAndTransactionDeletedTimeIsNull(id)
                .orElseThrow(NoSuchTransaction::new);

        return transactionMapper.transactionToTransactionDto(transaction);
    }

    @Override
    public List<Transaction> getExceededLimitTransactions(UUID accountId) {
        log.debug("Fetching all transactions that exceeded limit with bank id: {}", accountId);

        List<Transaction> transactionsList = transactionRepository.findAllByBankAccountIdAndLimitExceededIsTrue(accountId);

        if (transactionsList.isEmpty()) {
            log.warn("List is empty.");
        }

        return transactionsList;
    }

    @Override
    public void deleteTransaction(UUID id) {
        Transaction transaction = transactionRepository.findByIdAndTransactionDeletedTimeIsNull(id)
                .orElseThrow(NoSuchTransaction::new);

        log.debug("Deleting transaction with id: {}", id);

        transaction.setTransactionDeletedTime(LocalDateTime.now());
        transactionRepository.save(transaction);

        log.debug("Transaction with id: {} was deleted", id);
    }
}
