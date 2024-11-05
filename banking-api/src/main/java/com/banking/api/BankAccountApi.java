package com.banking.api;

import com.banking.dto.BankAccountDto;

import java.util.UUID;

public interface BankAccountApi {

    BankAccountDto getAccount(UUID id);

    BankAccountDto addNewAccount(BankAccountDto bankAccountDto);

    void deleteAccount(UUID id);
}
