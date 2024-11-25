package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.dao.BankAccountRepository;
import com.banking.bankingmicroservicetask.dao.TransactionRepository;
import com.banking.dto.TransactionDto;
import com.banking.bankingmicroservicetask.entity.BankAccount;
import com.banking.bankingmicroservicetask.entity.Transaction;
import com.banking.bankingmicroservicetask.exceptions.InvalidTransactionSumException;
import com.banking.bankingmicroservicetask.exceptions.NoSuchBankAccountException;
import com.banking.bankingmicroservicetask.exceptions.NoSuchTransaction;
import com.banking.bankingmicroservicetask.mappers.TransactionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Double transactionDtoSum = transactionDto.getTransactionSum();

        if (transactionDtoSum <= 0) {
            throw new InvalidTransactionSumException();
        }

        BankAccount bankAccount = bankAccountRepository.findById(transactionDto.getBankAccountId()).orElseThrow(NoSuchBankAccountException::new);
        Transaction transaction = transactionMapper.TransactionDtoToTransaction(transactionDto);

        TransactionCategoryStrategy categoryStrategy = TransactionCategoryFactory
                .getTransactionCategoryStrategy(transactionDto.getTransactionCategory());
        categoryStrategy.saveTransaction(bankAccount, transaction, transactionDtoSum);

        transactionRepository.save(transaction);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public TransactionDto getTransaction(UUID id) {
        log.debug("Fetching transaction with id: {}", id);

        Transaction transaction = transactionRepository.findByIdAndDeletedTimeIsNull(id)
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
        Transaction transaction = transactionRepository.findByIdAndDeletedTimeIsNull(id)
                .orElseThrow(NoSuchTransaction::new);

        log.debug("Deleting transaction with id: {}", id);

        transaction.setTransactionDeletedTime(LocalDateTime.now());
        transactionRepository.save(transaction);

        log.debug("Transaction with id: {} was deleted", id);
    }
}
