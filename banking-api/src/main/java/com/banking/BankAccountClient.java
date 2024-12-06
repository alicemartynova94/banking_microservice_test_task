package com.banking;

import com.banking.api.BankAccountApi;
import com.banking.dto.BankAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BankAccountClient implements BankAccountApi {

    private final RestTemplate restTemplate;

    @Override
    public BankAccountDto getAccount(@PathVariable UUID id) {
            return restTemplate.getForObject("/account/{id}", BankAccountDto.class, id);
    }

    @Override
    public List<BankAccountDto> getAllAccounts() {
        ResponseEntity<List<BankAccountDto>> response = restTemplate.exchange(
                "/accounts",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BankAccountDto>>() {}
        );
        return response.getBody();
    }

    @Override
    public BankAccountDto addNewAccount(BankAccountDto bankAccountDto) {
        return restTemplate.postForObject("/accounts", bankAccountDto, BankAccountDto.class);

    }

    @Override
    public void deleteAccount(UUID id) {
        restTemplate.delete("/account/{id}", id);
    }
}
