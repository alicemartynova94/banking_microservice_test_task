package org.example.services;

import com.banking.api.BankAccountOpenFeignClient;
import com.banking.dto.BankAccountDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/front/accounts")
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountOpenFeignClient bankAccountOpenFeignClient;

    @GetMapping("/{id}")
    public BankAccountDto getAccount(@PathVariable UUID id) {
        return bankAccountOpenFeignClient.getAccount(id);
    }

    @GetMapping
    public List<BankAccountDto> getAll() {
        return bankAccountOpenFeignClient.getAll();
    }

    @PostMapping
    public BankAccountDto addNewAccount(@RequestBody BankAccountDto bankAccountDto) {
        return bankAccountOpenFeignClient.addNewAccount(bankAccountDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable UUID id) {
        bankAccountOpenFeignClient.deleteAccount(id);
    }
}
