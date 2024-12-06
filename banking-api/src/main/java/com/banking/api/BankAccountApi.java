package com.banking.api;

import com.banking.dto.BankAccountDto;

import java.util.List;
import java.util.UUID;

public interface BankAccountApi {

    BankAccountDto getAccount(UUID id);

    List<BankAccountDto> getAllAccounts();

    BankAccountDto addNewAccount(BankAccountDto bankAccountDto);

    void deleteAccount(UUID id);
}
