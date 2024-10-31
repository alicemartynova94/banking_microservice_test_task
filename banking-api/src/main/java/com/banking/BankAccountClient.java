package com.banking;

import com.banking.api.BankAccountApi;
import com.banking.dto.BankAccountDto;

import java.util.UUID;

public class BankAccountClient implements BankAccountApi {
    @Override
    public BankAccountDto getAccount(UUID id) {
        return null;
    }

    @Override
    public BankAccountDto addNewAccount(BankAccountDto bankAccountDto) {
        return null;
    }

    @Override
    public void deleteAccount(UUID id) {

    }
}
