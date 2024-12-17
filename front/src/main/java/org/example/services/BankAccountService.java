package org.example.services;

import com.banking.api.BankAccountOpenFeignClient;
import com.banking.dto.BankAccountDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/front/accounts")
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountOpenFeignClient bankAccountOpenFeignClient;

    @GetMapping("/{id}")
    public Mono<BankAccountDto> getAccount(@PathVariable UUID id) {
        return bankAccountOpenFeignClient.getAccount(id);
    }

    @GetMapping
    public Flux<BankAccountDto> getAll() {
        return bankAccountOpenFeignClient.getAll();
    }

    @PostMapping
    public Mono<Void> addNewAccount(@RequestBody BankAccountDto bankAccountDto) {
        return bankAccountOpenFeignClient.addNewAccount(bankAccountDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAccount(@PathVariable UUID id) {
        return bankAccountOpenFeignClient.deleteAccount(id);
    }
}
