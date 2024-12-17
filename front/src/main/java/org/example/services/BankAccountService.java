package org.example.services;


import com.banking.BankAccountClient;
import com.banking.dto.BankAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountClient bankAccountClient;

    public List<BankAccountDto> getAll() {
        return bankAccountClient.getAllAccounts();
    }

    public BankAccountDto getById(String id) {
        UUID uuidId = UUID.fromString(id);
        return bankAccountClient.getAccount(uuidId);
    }

    public BankAccountDto save(BankAccountDto dto) {
        return bankAccountClient.addNewAccount(dto);
    }

    public void deleteAccount(String id) {
        UUID uuidId = UUID.fromString(id);
        bankAccountClient.deleteAccount(uuidId);
    }

}

