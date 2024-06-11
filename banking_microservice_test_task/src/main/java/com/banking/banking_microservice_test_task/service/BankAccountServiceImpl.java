package com.banking.banking_microservice_test_task.service;

import com.banking.banking_microservice_test_task.dao.BankAccountRepository;
import com.banking.banking_microservice_test_task.entity.BankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    private static final Logger logger = LoggerFactory.getLogger(BankAccountServiceImpl.class);
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public void saveAccount(BankAccount bankAccount) {
        logger.debug("Saving bank account: {}", bankAccount);
        bankAccountRepository.save(bankAccount);
        logger.debug("Saved bank account with id: {}", bankAccount.getId());
    }

    @Override
    public BankAccount getAccount(UUID id) {
        logger.debug("Fetching bank account with id: {}", id);
        BankAccount bankAccount = null;
        Optional<BankAccount> bankAccountOptional = bankAccountRepository.findById(id);
        if (bankAccountOptional.isPresent()) {
            bankAccount = bankAccountOptional.get();
        }

        logger.debug("Found bank account: {}", bankAccount);
        return bankAccount;
    }

    @Override
    public void deleteAccount(UUID id) {
        logger.debug("Deleting bank account with id: {}", id);
        bankAccountRepository.deleteById(id);
        logger.debug("Deleted bank account with id: {}", id);
    }
}
