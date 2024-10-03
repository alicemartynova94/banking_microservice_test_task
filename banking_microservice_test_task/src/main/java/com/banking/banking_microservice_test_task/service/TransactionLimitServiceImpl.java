package com.banking.banking_microservice_test_task.service;

import com.banking.banking_microservice_test_task.dao.TransactionLimitRepository;
import com.banking.banking_microservice_test_task.dto.TransactionLimitDto;
import com.banking.banking_microservice_test_task.entity.TransactionCategory;
import com.banking.banking_microservice_test_task.entity.TransactionLimit;
import com.banking.banking_microservice_test_task.exception_handling.LimitUpdateFrequencyExceededException;
import com.banking.banking_microservice_test_task.exception_handling.LimitUpdateNotAllowedException;
import com.banking.banking_microservice_test_task.exception_handling.NoSuchLimitException;
import com.banking.banking_microservice_test_task.mappers.TransactionLimitMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@Slf4j
public class TransactionLimitServiceImpl implements TransactionLimitService {
    @Autowired
    private TransactionLimitRepository transactionLimitRepository;
    @Autowired
    private TransactionLimitMapper transactionLimitMapper;

    @Override
    public void saveLimit(TransactionLimitDto limitDto) {
        log.debug("Saving limit: {}", limitDto);

        TransactionLimit transactionLimit = transactionLimitMapper.limitDtoToLimit(limitDto);
        transactionLimitRepository.save(transactionLimit);

        log.debug("Saved limit with id: {}", transactionLimit.getId());
    }

    @Override
    public TransactionLimitDto getLimit(UUID id) {
        log.debug("Fetching limit with id: {}", id);

        TransactionLimit transactionLimit = transactionLimitRepository.findById(id).orElseThrow(() -> new NoSuchLimitException("Limit with this id is not found."));

        return transactionLimitMapper.limitToLimitDto(transactionLimit);
    }

    @Override
    public void updateLimit(UUID id, TransactionLimitDto transactionLimitDto) {
        log.debug("Fetching limit with id: {}", id);
        TransactionLimit transactionLimit = transactionLimitRepository.findById(id).orElseThrow(() -> new NoSuchLimitException("Limit with this id is not found."));
        Double limitSum = transactionLimitDto.getLimitSum();

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime lastUpdated = transactionLimit.getLimitLastUpdateTime();

        log.debug("Attempting to update limit with sum: {}", limitSum);

        if (today.getDayOfMonth() != 15) {
            throw new LimitUpdateNotAllowedException("Limit updates are only allowed on the 15th of each month.");
        }
        if (today.getMonth() == lastUpdated.getMonth() && today.getYear() == lastUpdated.getYear()) {
            throw new LimitUpdateFrequencyExceededException("You've already created limit this month.");
        }
        transactionLimit.setLimitSum(limitSum);
        transactionLimit.setTransactionCategory(transactionLimitDto.getTransactionCategory());
        transactionLimitRepository.save(transactionLimit);

        log.debug("Successfully updated limit with id: {} to {}", id, limitSum);
    }

    @Override
    public void deleteLimit(UUID id) {
        log.debug("Fetching limit with id: {}", id);
        transactionLimitRepository.findById(id).orElseThrow(() -> new NoSuchLimitException("Limit with this id is not found."));

        log.debug("Deleting limit with id: {}", id);
        transactionLimitRepository.deleteById(id);

        log.debug("Deleted limit with id: {}", id);
    }
}
