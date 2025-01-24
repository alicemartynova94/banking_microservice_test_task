package com.banking.bankingmicroservicetask.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
@Slf4j
public class BankAccountGlobalExceptionHandler {

    @ExceptionHandler(NoSuchBankAccountException.class)
    public Mono<ResponseEntity<String>> handleNoSuchBankAccount(NoSuchBankAccountException ex) {
        return Mono.just(ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage()));
    }

    @ExceptionHandler(InvalidTransactionSumException.class)
    public Mono<ResponseEntity<String>> handleInvalidTransactionSum(InvalidTransactionSumException ex) {
        return Mono.just(ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ex.getMessage()));
    }
}
