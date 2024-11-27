package org.example.services;

import com.banking.dto.BankAccountDto;
import org.springframework.stereotype.Service;

import java.nio.file.FileSystemNotFoundException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class BankAccountService {

    private final List<BankAccountDto> dtos = new ArrayList<>();

    public List<BankAccountDto> getAll() {
        if (dtos.isEmpty()) {
            IntStream.range(1, 15).forEach(id -> {
                var bankAccountDto = temporaryAccount(id);
                dtos.add(bankAccountDto);
            });
        }
        return dtos;
    }

    public BankAccountDto getById(String id){
        return dtos.stream()
                .filter(a-> a.getId().toString().equals(id))
                .findFirst()
                .orElseThrow(()-> new FileSystemNotFoundException("error"));
    }

    private BankAccountDto temporaryAccount(Integer id) {
        Random random = new Random();
        int monthIndex = random.nextInt(Month.values().length);
        Month randomMonth = Month.values()[monthIndex];
        return BankAccountDto.builder()
                .id(UUID.randomUUID())
                .accountHolder(randomMonth.toString())
                .availableFunds(random.nextDouble())
                .accountNumber(id)
                .build();
    }

}
