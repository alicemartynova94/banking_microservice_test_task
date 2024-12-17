package com.banking.bankingmicroservicetask.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@ControllerAdvice
@Slf4j
public class TransactionLimitGlobalExceptionHandler {
    @ExceptionHandler(NoSuchLimitException.class)
    public Mono<ServerResponse> handleNoSuchLimitException(NoSuchLimitException exception) {
        log.error("NoSuchLimitException: {}", exception.getMessage());
        return ServerResponse
            .status(HttpStatus.NOT_FOUND) // Возвращаем статус 404
            .bodyValue(exception.getMessage());
    }

    @ExceptionHandler(LimitUpdateNotAllowedException.class)
    public Mono<ServerResponse> handleLimitUpdateNotAllowedException(LimitUpdateNotAllowedException exception) {
        log.warn("LimitUpdateNotAllowedException: {}", exception.getMessage());
        return ServerResponse
            .status(HttpStatus.FORBIDDEN) // Возвращаем статус 403
            .bodyValue(exception.getMessage());
    }

    @ExceptionHandler(LimitUpdateFrequencyExceededException.class)
    public Mono<ServerResponse> handleLimitUpdateFrequencyExceededException(LimitUpdateFrequencyExceededException exception) {
        log.warn("LimitUpdateFrequencyExceededException: {}", exception.getMessage());
        return ServerResponse
            .status(HttpStatus.CONFLICT) // Возвращаем статус 409
            .bodyValue(exception.getMessage());
    }
}
