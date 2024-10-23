package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.dto.TransactionLimitDto;

import java.util.UUID;

public interface TransactionLimitService {
    public void saveLimit(TransactionLimitDto limitDto);

    public TransactionLimitDto getLimit(UUID id);

    public void updateLimit(UUID id, TransactionLimitDto transactionLimitDto);

    public void deleteLimit(UUID id);
}
