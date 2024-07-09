package com.banking.banking_microservice_test_task.service;

import com.banking.banking_microservice_test_task.dao.BankAccountRepository;
import com.banking.banking_microservice_test_task.entity.BankAccount;
import com.banking.banking_microservice_test_task.exception_handling.NoSuchBankAccountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public void saveAccount(BankAccount bankAccount) {
        log.debug("Saving bank account: {}", bankAccount);

        bankAccountRepository.save(bankAccount);

        log.debug("Saved bank account with id: {}", bankAccount.getId());
    }

    @Override
    public BankAccount getAccount(UUID id) {
        log.debug("Fetching bank account with id: {}", id);

        return bankAccountRepository.findById(id).orElseThrow(() -> new NoSuchBankAccountException("There is no bank account with such id"));
    }

    @Override
    public void deleteAccount(UUID id) {
        bankAccountRepository.findById(id).orElseThrow(() -> new NoSuchBankAccountException("There is no bank account with such id"));

        log.debug("Deleting bank account with id: {}", id);

        bankAccountRepository.deleteById(id);

        log.debug("Deleted bank account with id: {}", id);
    }
}
