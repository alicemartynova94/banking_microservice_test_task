package com.banking.bankingmicroservicetask.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@ControllerAdvice
@Slf4j
public class TransactionGlobalHandler {
    @ExceptionHandler(InvalidTransactionSumException.class)
    public Mono<ServerResponse> handleInvalidTransactionSumException(InvalidTransactionSumException exception) {
        log.error("InvalidTransactionSumException: {}", exception.getMessage());
        return ServerResponse
            .status(HttpStatus.BAD_REQUEST)
            .bodyValue(exception.getMessage());
    }

    @ExceptionHandler(InvalidTransactionTypeException.class)
    public Mono<ServerResponse> handleInvalidTransactionTypeException(InvalidTransactionTypeException exception) {
        log.error("InvalidTransactionTypeException: {}", exception.getMessage());
        return ServerResponse
            .status(HttpStatus.BAD_REQUEST)
            .bodyValue(exception.getMessage());
    }
}
