//package com.banking.bankingmicroservicetask.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.http.HttpStatus.CREATED;
//
//import com.banking.bankingmicroservicetask.service.BankAccountService;
//import com.banking.dto.BankAccountDto;
//import com.banking.dto.CurrencyShortnameDto;
//import com.banking.dto.TransactionLimitDto;
//import java.util.UUID;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import reactor.core.publisher.Mono;
//
//@WebFluxTest(controllers = BankAccountController.class)
//class BankAccountControllerTest {
//
//  private WebTestClient webTestClient;
//  @MockBean
//  private BankAccountService bankAccountService;
//
//  private UUID accountId;
//  private BankAccountDto bankAccountDto;
//
//  @BeforeEach
//  void init() {
//    webTestClient = WebTestClient.bindToController(new BankAccountController(bankAccountService)).build();
//    UUID currencyId = UUID.randomUUID();
//    Short currencyName = (short) 1;
//
//    CurrencyShortnameDto currencyShortnameDto = new CurrencyShortnameDto(currencyId, currencyName);
//
//    accountId = UUID.randomUUID();
//    Integer accountNumber = 1111111;
//    String accountHolder = "Smirnov Dmitry";
//    Double availableFunds = 0.0;
//
//    bankAccountDto = new BankAccountDto(
//        accountId,
//        accountNumber,
//        accountHolder,
//        availableFunds,
//        currencyShortnameDto,
//        new TransactionLimitDto(),
//        new TransactionLimitDto());
//  }
//
//  @Test
//  void getBankAccountByIdExpect200Status() throws Exception {
//    given(bankAccountService.getAccount(accountId)).willReturn(Mono.just(bankAccountDto));
//
//    webTestClient.get()
//        .uri("/api/accounts/{id}", accountId)
//        .exchange()
//        .expectStatus().isOk()
//        .expectBody()
//        .jsonPath("$.accountNumber").isEqualTo(bankAccountDto.getAccountNumber())
//        .jsonPath("$.accountHolder").isEqualTo(bankAccountDto.getAccountHolder())
//        .jsonPath("$.availableFunds").isEqualTo(bankAccountDto.getAvailableFunds())
//        .jsonPath("$.currencyShortname.currencyNameNumeric").isEqualTo(1);
//  }
//
//  @Test
//  void addNewAccountExpect201Status() throws Exception {
//    given(bankAccountService.saveAccount(any(BankAccountDto.class))).willReturn(Mono.empty());
//
//    webTestClient.post()
//        .uri("/api/accounts")
//        .contentType(MediaType.APPLICATION_JSON)
//        .bodyValue(bankAccountDto)
//        .exchange()
//        .expectStatus().isEqualTo(CREATED);
//  }
//
//  @Test
//  void deleteAccountByIdExpect200Status() throws Exception {
//    given(bankAccountService.deleteAccount(accountId)).willReturn(Mono.empty());
//
//    webTestClient.delete()
//        .uri("/api/accounts/{id}", accountId)
//        .exchange()
//        .expectStatus().isNoContent();
//  }
//}
