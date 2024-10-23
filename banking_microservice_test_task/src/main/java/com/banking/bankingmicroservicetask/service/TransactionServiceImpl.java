package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.dao.BankAccountRepository;
import com.banking.bankingmicroservicetask.dao.TransactionRepository;
import com.banking.bankingmicroservicetask.dto.TransactionDto;
import com.banking.bankingmicroservicetask.entity.BankAccount;
import com.banking.bankingmicroservicetask.entity.Transaction;
import com.banking.bankingmicroservicetask.exception_handling.InvalidTransactionSumException;
import com.banking.bankingmicroservicetask.exception_handling.InvalidTransactionTypeException;
import com.banking.bankingmicroservicetask.exception_handling.NoSuchBankAccountException;
import com.banking.bankingmicroservicetask.mappers.TransactionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
        BankAccount bankAccount = bankAccountRepository.findById(transactionDto.getBankAccountId()).orElseThrow(() -> new NoSuchBankAccountException("There is no account for the transaction with such id"));
        Transaction transaction = transactionMapper.TransactionDtoToTransaction(transactionDto);

        Double limitServices = bankAccount.getLimitServices();
        Double limitGoods = bankAccount.getLimitGoods();
        Double transactionDtoSum = transactionDto.getTransactionSum();
        Double accountAvailableFunds = bankAccount.getAvailableFunds();
        List<Transaction> transactionList = bankAccount.getTransaction();

        if (transactionList == null) {
            transactionList = new ArrayList<>();
        }

        if (transactionDtoSum <= 0 || transactionDtoSum == null) {
            throw new InvalidTransactionSumException("Transaction sum cannot be a negative number or null.");
        }

        switch (transactionDto.getTransactionCategory()) {
            case SERVICES:
                if (transactionDtoSum <= limitServices) {
                    limitServices -= transactionDtoSum;
                    bankAccount.setLimitServices(limitServices);
                    bankAccount.setAvailableFunds(accountAvailableFunds - transactionDtoSum);
                    transactionList.add(transaction);
                    transaction.setLimitExceeded(false);
                } else {

                    transaction.setLimitExceeded(true);
                    System.out.println("This transaction will be saved to your transactions' history but " +
                            "won't be performed due to your limit policy");
                }
                transactionRepository.save(transaction);
                bankAccountRepository.save(bankAccount);
                break;
            case GOODS:
                if (transactionDtoSum <= limitGoods) {
                    limitGoods -= transactionDtoSum;
                    bankAccount.setLimitGoods(limitGoods);
                    bankAccount.setAvailableFunds(accountAvailableFunds - transactionDtoSum);
                    transactionList.add(transaction);
                    transaction.setLimitExceeded(false);
                } else {
                    transaction.setLimitExceeded(true);
                    System.out.println("This transaction will be saved to your transactions' history but " +
                            "won't be performed due to your limit policy");
                }
                transactionRepository.save(transaction);
                bankAccountRepository.save(bankAccount);
                break;
            default:
                throw new InvalidTransactionTypeException("There are only two transaction types available: GOODS and SERVICES.");
        }
    }

    @Override
    public TransactionDto getTransaction(UUID id) {
        log.debug("Fetching transaction with id: {}", id);

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no transaction with such id"));

        return transactionMapper.transactionToTransactionDto(transaction);
    }

    @Override
    public List<Transaction> getExceededLimitTransactions(UUID accountId) {
        log.debug("Fetching all transactions that exceeded limit with bank id: {}", accountId);

        List<Transaction> transactionsList = transactionRepository.findExceededLimitTransactionsByBankAccountId(accountId);

        if (transactionsList.isEmpty()) {
            System.out.println("There are no transactions that exceeded limit.");
            return Collections.emptyList();
        }

        return transactionsList;
    }

    @Override
    public void deleteTransaction(UUID id) {
        transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no transaction with such id"));

        log.debug("Deleting transaction with id: {}", id);

        transactionRepository.deleteById(id);

        log.debug("Transaction with id: {} was deleted", id);
    }
}
