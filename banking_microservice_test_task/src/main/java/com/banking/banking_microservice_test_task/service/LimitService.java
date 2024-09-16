package com.banking.banking_microservice_test_task.service;

import com.banking.banking_microservice_test_task.dto.LimitDto;

import java.util.UUID;

public interface LimitService {
    public void saveLimit(LimitDto limitDto);

    public LimitDto getLimit(UUID id);

    public void updateLimit(UUID id);

    public void deleteLimit(UUID id);
}
