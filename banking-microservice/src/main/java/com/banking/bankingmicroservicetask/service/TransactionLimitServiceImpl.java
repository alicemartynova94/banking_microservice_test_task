package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.dao.TransactionLimitRepository;
import com.banking.bankingmicroservicetask.exceptions.LimitUpdateFrequencyExceededException;
import com.banking.bankingmicroservicetask.exceptions.LimitUpdateNotAllowedException;
import com.banking.bankingmicroservicetask.exceptions.NoSuchLimitException;
import com.banking.bankingmicroservicetask.mappers.TransactionLimitMapper;
import com.banking.dto.TransactionLimitDto;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionLimitServiceImpl implements TransactionLimitService {

  private final TransactionLimitRepository transactionLimitRepository;
  private final TransactionLimitMapper transactionLimitMapper;

  @Override
  public Mono<Void> saveLimit(TransactionLimitDto limitDto) {
    log.debug("Saving limit: {}", limitDto);
    return Mono.just(limitDto)
        .map(transactionLimitMapper::limitDtoToLimit)
        .flatMap(transactionLimitRepository::save)
        .doOnSuccess(limit -> log.debug("Saved limit with id: {}", limit.getId()))
        .then();
  }

  @Override
  public Mono<TransactionLimitDto> getLimit(UUID id) {
    log.debug("Fetching limit with id: {}", id);
    return transactionLimitRepository.findByIdAndLimitDeletedTimeIsNull(id)
        .switchIfEmpty(Mono.error(NoSuchLimitException::new))
        .map(transactionLimitMapper::limitToLimitDto);
  }

  @Override
  public Mono<Void> updateLimit(UUID id, TransactionLimitDto transactionLimitDto) {
    log.debug("Fetching limit with id: {}", id);

    return transactionLimitRepository.findByIdAndLimitDeletedTimeIsNull(id)
        .switchIfEmpty(Mono.error(new NoSuchLimitException()))
        .flatMap(transactionLimit -> {
          Double limitSum = transactionLimitDto.getLimitSum();
          LocalDateTime today = LocalDateTime.now();
          LocalDateTime lastUpdated = transactionLimit.getLimitLastUpdateTime();

          log.debug("Attempting to update limit with sum: {}", limitSum);

          if (today.getDayOfMonth() != 15) {
            return Mono.error(new LimitUpdateNotAllowedException());
          }
          if (today.getMonth() == lastUpdated.getMonth() && today.getYear() == lastUpdated.getYear()) {
            return Mono.error(new LimitUpdateFrequencyExceededException());
          }

          transactionLimit.setLimitSum(limitSum);
          transactionLimit.setTransactionCategory(transactionLimitDto.getTransactionCategory());

          return transactionLimitRepository.save(transactionLimit)
              .doOnSuccess(savedLimit ->
                  log.debug("Successfully updated limit with id: {} to {}", id, limitSum)
              )
              .then();
        });
  }

  @Override
  public Mono<Void> deleteLimit(UUID id) {
    //TODO Log can be added via AOP
    log.debug("Fetching limit with id: {}", id);
    return transactionLimitRepository.findByIdAndLimitDeletedTimeIsNull(id)
        .switchIfEmpty(Mono.error(NoSuchLimitException::new))
        .flatMap(transactionLimit -> {
          transactionLimit.setLimitDeletedTime(LocalDateTime.now());
          return transactionLimitRepository.save(transactionLimit);
        })
        .doOnSuccess(v -> log.debug("Deleted limit with id: {}", id))
        .doOnError(error -> log.error("Error occurred while deleting limit with id: {}", id, error))
        .then();
  }
}
