package com.banking.banking_microservice_test_task.service;

import com.banking.banking_microservice_test_task.dao.BankAccountRepository;
import com.banking.banking_microservice_test_task.dto.BankAccountDto;
import com.banking.banking_microservice_test_task.entity.BankAccount;
import com.banking.banking_microservice_test_task.exception_handling.NoSuchBankAccountException;
import com.banking.banking_microservice_test_task.mappers.BankAccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private BankAccountMapper bankAccountMapper;

    @Override
    public void saveAccount(BankAccountDto bankAccountDto) {
        log.debug("Saving bank account: {}", bankAccountDto);

        BankAccount bankAccount = bankAccountMapper.bankAccountDtoToBankAccount(bankAccountDto);

        bankAccountRepository.save(bankAccount);

        log.debug("Saved bank account with id: {}", bankAccount.getId());
    }

    @Override
    public BankAccountDto getAccount(UUID id) {
        log.debug("Fetching bank account with id: {}", id);

        BankAccount bankAccount = bankAccountRepository.findById(id).orElseThrow(() -> new NoSuchBankAccountException("There is no bank account with such id"));

        return bankAccountMapper.bankAccountToBankAccountDto(bankAccount);
    }

    @Override
    public void deleteAccount(UUID id) {
        bankAccountRepository.findById(id).orElseThrow(() -> new NoSuchBankAccountException("There is no bank account with such id"));

        log.debug("Deleting bank account with id: {}", id);

        bankAccountRepository.deleteById(id);

        log.debug("Deleted bank account with id: {}", id);
    }

    @Scheduled(cron = "#{@bankAccountServiceProperties.cron}")
    public void updateLimits() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        for (BankAccount account : accounts) {
            account.setLimitServices(account.getTransactionLimitServices().getLimitSum());
            account.setLimitGoods(account.getTransactionLimitGoods().getLimitSum());
            bankAccountRepository.saveAll(accounts);
        }
    }
}
