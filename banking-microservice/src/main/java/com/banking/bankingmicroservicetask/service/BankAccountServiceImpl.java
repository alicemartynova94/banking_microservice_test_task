package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.dao.BankAccountRepository;
import com.banking.dto.BankAccountDto;
import com.banking.bankingmicroservicetask.entity.BankAccount;
import com.banking.bankingmicroservicetask.exceptions.NoSuchBankAccountException;
import com.banking.bankingmicroservicetask.mappers.BankAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {


    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

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

        BankAccount bankAccount = bankAccountRepository.findByIdAndBankAccountDeletedTimeIsNull(id).orElseThrow(NoSuchBankAccountException::new);

        return bankAccountMapper.bankAccountToBankAccountDto(bankAccount);
    }

    @Override
    public List<BankAccountDto> getAllAccounts() {
      return bankAccountRepository.findByBankAccountDeletedTimeIsNull()
              .stream()
              .map(bankAccountMapper::bankAccountToBankAccountDto)
              .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(UUID id) {
        BankAccount bankAccount = bankAccountRepository.findByIdAndBankAccountDeletedTimeIsNull(id).orElseThrow(NoSuchBankAccountException::new);

        log.debug("Deleting bank account with id: {}", id);

        bankAccount.setBankAccountDeletedTime(LocalDateTime.now());
        bankAccountRepository.save(bankAccount);

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
