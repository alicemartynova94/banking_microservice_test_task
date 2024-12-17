package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.dao.BankAccountRepository;
import com.banking.bankingmicroservicetask.dao.TransactionLimitRepository;
import com.banking.bankingmicroservicetask.entity.BankAccount;
import com.banking.bankingmicroservicetask.entity.TransactionLimit;
import com.banking.bankingmicroservicetask.exceptions.NoSuchBankAccountException;
import com.banking.bankingmicroservicetask.mappers.BankAccountMapper;
import com.banking.dto.BankAccountDto;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

  private final BankAccountRepository bankAccountRepository;
  private final BankAccountMapper bankAccountMapper;
  private final TransactionLimitRepository transactionLimitRepository;

  @Override
  public Mono<Void> saveAccount(BankAccountDto bankAccountDto) {
    log.debug("Saving bank account: {}", bankAccountDto);

    BankAccount bankAccount = bankAccountMapper.bankAccountDtoToBankAccount(bankAccountDto);

    return bankAccountRepository.save(bankAccount)
        .doOnNext(savedAccount -> log.debug("Saved bank account with id: {}", savedAccount.getId()))
        .then();
  }

  @Override
  public Mono<BankAccountDto> getAccount(UUID id) {
    log.debug("Fetching bank account with id: {}", id);

    return bankAccountRepository.findByIdAndBankAccountDeletedTimeIsNull(id)
        .switchIfEmpty(Mono.error(new NoSuchBankAccountException()))
        .map(bankAccountMapper::bankAccountToBankAccountDto)
        .doOnNext(account -> log.debug("Fetched bank account with id: {}", id));
  }

  @Override
  public Mono<Void> deleteAccount(UUID id) {
    return bankAccountRepository.findByIdAndBankAccountDeletedTimeIsNull(id)
        .switchIfEmpty(Mono.error(() -> {
          throw new NoSuchBankAccountException();
        }))
        .flatMap(account -> {
          account.setBankAccountDeletedTime(LocalDateTime.now());
          return bankAccountRepository.save(account);
        })
        .doOnSuccess(account -> log.debug("Deleted bank account with id: {}", id))
        .then();
  }

  @Override
  public Flux<BankAccountDto> getAll() {
    log.debug("Fetching all bank accounts");
    return bankAccountRepository.findAll()
        .map(bankAccountMapper::bankAccountToBankAccountDto);
  }

  @Scheduled(cron = "#{@bankAccountServiceProperties.cron}")
  public void updateLimits() {
    bankAccountRepository.findAll()
        .publishOn(Schedulers.boundedElastic())
        .flatMap(account -> {
          Mono<TransactionLimit> goodsLimit = transactionLimitRepository
              .findById(account.getTransactionLimitGoodsId())
              .switchIfEmpty(
                  Mono.error(new RuntimeException("Goods TransactionLimit not found for account: " + account.getId())));
          Mono<TransactionLimit> servicesLimit = transactionLimitRepository
              .findById(account.getTransactionLimitServicesId())
              .switchIfEmpty(
                  Mono.error(
                      new RuntimeException("Services TransactionLimit not found for account: " + account.getId())));
          return goodsLimit.zipWith(servicesLimit, (goods, services) -> {
                account.setLimitGoods(goods.getLimitSum());
                account.setLimitServices(services.getLimitSum());
                return account;
              })
              .flatMap(bankAccountRepository::save);
        }).blockLast();
  }
}
