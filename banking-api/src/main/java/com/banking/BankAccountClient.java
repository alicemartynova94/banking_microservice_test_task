package com.banking;

import com.banking.api.BankAccountApi;
import com.banking.dto.BankAccountDto;
import com.banking.wrapper.BankAccountDtoList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BankAccountClient implements BankAccountApi {

    private final RestTemplate restTemplate;

    @Override
    public BankAccountDto getAccount(UUID id) {
            return restTemplate.getForObject("/account/{id}", BankAccountDto.class, id);
    }

    @Override
    public List<BankAccountDto> getAllAccounts() {
        BankAccountDtoList response = restTemplate.getForObject("/accounts", BankAccountDtoList.class);
        return response.getAccountDtos();
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
