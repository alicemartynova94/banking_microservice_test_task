package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.dao.TransactionLimitRepository;
import com.banking.dto.TransactionLimitDto;
import com.banking.bankingmicroservicetask.entity.TransactionLimit;
import com.banking.bankingmicroservicetask.exceptions.LimitUpdateFrequencyExceededException;
import com.banking.bankingmicroservicetask.exceptions.LimitUpdateNotAllowedException;
import com.banking.bankingmicroservicetask.exceptions.NoSuchLimitException;
import com.banking.bankingmicroservicetask.mappers.TransactionLimitMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionLimitServiceImpl implements TransactionLimitService {

    private final TransactionLimitRepository transactionLimitRepository;
    private final TransactionLimitMapper transactionLimitMapper;

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

        TransactionLimit transactionLimit = transactionLimitRepository.findByIdActiveLimit(id).orElseThrow(NoSuchLimitException::new);

        return transactionLimitMapper.limitToLimitDto(transactionLimit);
    }

    @Override
    public void updateLimit(UUID id, TransactionLimitDto transactionLimitDto) {
        log.debug("Fetching limit with id: {}", id);
        TransactionLimit transactionLimit = transactionLimitRepository.findByIdActiveLimit(id).orElseThrow(NoSuchLimitException::new);
        Double limitSum = transactionLimitDto.getLimitSum();

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime lastUpdated = transactionLimit.getLimitLastUpdateTime();

        log.debug("Attempting to update limit with sum: {}", limitSum);

        if (today.getDayOfMonth() != 15) {
            throw new LimitUpdateNotAllowedException();
        }
        if (today.getMonth() == lastUpdated.getMonth() && today.getYear() == lastUpdated.getYear()) {
            throw new LimitUpdateFrequencyExceededException();
        }
        transactionLimit.setLimitSum(limitSum);
        transactionLimit.setTransactionCategory(transactionLimitDto.getTransactionCategory());
        transactionLimitRepository.save(transactionLimit);

        log.debug("Successfully updated limit with id: {} to {}", id, limitSum);
    }

    @Override
    public void deleteLimit(UUID id) {
        log.debug("Fetching limit with id: {}", id);
        TransactionLimit transactionLimit = transactionLimitRepository.findByIdActiveLimit(id).orElseThrow(NoSuchLimitException::new);

        log.debug("Deleting limit with id: {}", id);
        transactionLimit.setLimitDeletedTime(LocalDateTime.now());
        transactionLimitRepository.save(transactionLimit);

        log.debug("Deleted limit with id: {}", id);
    }
}
