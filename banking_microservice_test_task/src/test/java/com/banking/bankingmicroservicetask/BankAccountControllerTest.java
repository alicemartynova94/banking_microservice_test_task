package com.banking.bankingmicroservicetask;


import com.banking.bankingmicroservicetask.dto.BankAccountDto;
import com.banking.bankingmicroservicetask.dto.CurrencyShortnameDto;
import com.banking.bankingmicroservicetask.service.BankAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.UUID;

@WebMvcTest
public class BankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService bankAccountService;

    @Autowired
    ObjectMapper objectMapper;
    private UUID accountId;
    private BankAccountDto bankAccountDto;

    @BeforeEach
    void init() {
        UUID currencyId = UUID.randomUUID();
        Short currencyName = (short) 1;

        CurrencyShortnameDto currencyShortnameDto = new CurrencyShortnameDto(currencyId, currencyName);

        accountId = UUID.randomUUID();
        Integer accountNumber = 1111111;
        String accountHolder = "Smirnov Dmitry";
        Double availableFunds = 0.0;

        bankAccountDto = new BankAccountDto(accountId, accountNumber, accountHolder, availableFunds, currencyShortnameDto);
    }


    @Test
    public void getBankAccountByIdExpect200Status() throws Exception {
        given(bankAccountService.getAccount(accountId)).willReturn(bankAccountDto);

        mockMvc.perform(get("/api/account/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber", is(bankAccountDto.getAccountNumber())))
                .andExpect(jsonPath("$.accountHolder", is(bankAccountDto.getAccountHolder())))
                .andExpect(jsonPath("$.availableFunds", is(bankAccountDto.getAvailableFunds())))
                .andExpect(jsonPath("$.currencyShortname.currencyNameNumeric", is(1)));
    }


    @Test
    public void addNewAccountExpect201Status() throws Exception {
        doAnswer(invocationOnMock -> {
            BankAccountDto accountDto = invocationOnMock.getArgument(0);
            return null;
        }).when(bankAccountService).saveAccount(any(BankAccountDto.class));

        ResultActions resultActions = mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bankAccountDto)));

        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountNumber", is(bankAccountDto.getAccountNumber())))
                .andExpect(jsonPath("$.accountHolder", is(bankAccountDto.getAccountHolder())))
                .andExpect(jsonPath("$.availableFunds", is(bankAccountDto.getAvailableFunds())))
                .andExpect(jsonPath("$.currencyShortname.currencyNameNumeric", is(1)));
    }

    @Test
    public void deleteAccountByIdExpect200Status() throws Exception {
        doNothing().when(bankAccountService).deleteAccount(accountId);

        mockMvc.perform(delete("/api/account/{id}", accountId))
                .andExpect(status().isOk());
    }
}
