package com.banking.banking_microservice_test_task.service;

import com.banking.banking_microservice_test_task.dao.TransactionRepository;
import com.banking.banking_microservice_test_task.dto.TransactionDto;
import com.banking.banking_microservice_test_task.entity.Transaction;
import com.banking.banking_microservice_test_task.mappers.TransactionMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    @Transactional
    public void saveTransaction(TransactionDto transactionDto) {
        log.debug("Saving transaction: {}", transactionDto);
        Transaction transaction = transactionMapper.TransactionDtoToTransaction(transactionDto);
        transactionRepository.save(transaction);
        log.debug("Transaction with id: {} is saved", transaction.getId());
    }

    @Override
    public TransactionDto getTransaction(UUID id) {
        log.debug("Fetching transaction with id: {}", id);

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no transaction with such id"));

        return transactionMapper.transactionToTransactionDto(transaction);
    }

    @Override
    public List<TransactionDto> getListOfTransactions() {
        List<Transaction> transactionList = transactionRepository.findAll();

        return transactionList.stream()
                .map(t -> transactionMapper.transactionToTransactionDto(t)).toList();
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
