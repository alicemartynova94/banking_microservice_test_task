package com.banking.banking_microservice_test_task.service;

import com.banking.banking_microservice_test_task.dao.TransactionRepository;
import com.banking.banking_microservice_test_task.dto.TransactionDto;
import com.banking.banking_microservice_test_task.entity.BankAccount;
import com.banking.banking_microservice_test_task.entity.Transaction;
import com.banking.banking_microservice_test_task.exception_handling.InvalidTransactionSumException;
import com.banking.banking_microservice_test_task.exception_handling.InvalidTransactionTypeException;
import com.banking.banking_microservice_test_task.exception_handling.NoSuchBankAccountException;
import com.banking.banking_microservice_test_task.mappers.TransactionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        BankAccount bankAccount = transactionRepository.findByTransactionIdBankAccount(transactionDto.getId()).orElseThrow(() -> new NoSuchBankAccountException("There is no account for the transaction with such id"));
        Transaction transaction = transactionMapper.TransactionDtoToTransaction(transactionDto);

        Double limitServices = bankAccount.getLimitServices();
        Double limitGoods = bankAccount.getLimitGoods();
        Double transactionDtoSum = transactionDto.getTransactionSum();
        Double accountAvailableFunds = bankAccount.getAvailableFunds();

        if (transactionDtoSum <= 0 || transactionDtoSum == null) {
            throw new InvalidTransactionSumException("Transaction sum cannot be a negative number or null.");
        }

        switch (transactionDto.getTransactionCategory()) {
            case SERVICES:
                if (transactionDtoSum <= limitServices) {
                    limitServices -= transactionDtoSum;
                    bankAccount.setLimitServices(limitServices);
                    bankAccount.setAvailableFunds(accountAvailableFunds - transactionDtoSum);
                    transaction.setLimitExceeded(false);
                } else {

                    transaction.setLimitExceeded(true);
                    System.out.println("This transaction will be saved to your transactions' history but " +
                            "won't be performed due to your limit policy");
                }
                transactionRepository.save(transaction);
                break;
            case GOODS:
                if (transactionDtoSum <= limitGoods) {
                    limitGoods -= transactionDtoSum;
                    bankAccount.setLimitGoods(limitGoods);
                    bankAccount.setAvailableFunds(accountAvailableFunds - transactionDtoSum);
                    transaction.setLimitExceeded(false);
                } else {
                    transaction.setLimitExceeded(true);
                    System.out.println("This transaction will be saved to your transactions' history but " +
                            "won't be performed due to your limit policy");
                }
                transactionRepository.save(transaction);
                break;
            case null, default:
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
    public void deleteTransaction(UUID id) {
        transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no transaction with such id"));

        log.debug("Deleting transaction with id: {}", id);

        transactionRepository.deleteById(id);

        log.debug("Transaction with id: {} was deleted", id);
    }
}
