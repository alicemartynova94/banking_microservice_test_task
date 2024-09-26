package com.banking.banking_microservice_test_task.service;

import com.banking.banking_microservice_test_task.dto.TransactionLimitDto;

import java.util.UUID;

public interface LimitService {
    public void saveLimit(TransactionLimitDto limitDto);

    public TransactionLimitDto getLimit(UUID id);

    public void updateLimit(UUID id);

    public void deleteLimit(UUID id);
}
