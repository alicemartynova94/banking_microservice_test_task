package com.banking.bankingmicroservicetask.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class TransactionLimitGlobalExceptionHandler {
    @ExceptionHandler({NoSuchLimitException.class})
    public ResponseEntity<Object> handleNoSuchLimitException(NoSuchLimitException exception) {
        log.error("NoSuchLimitException: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({LimitUpdateNotAllowedException.class})
    public ResponseEntity<Object> handleLimitUpdateNotAllowedException(LimitUpdateNotAllowedException exception) {
        log.warn("LimitUpdateNotAllowedException: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exception.getMessage());
    }

    @ExceptionHandler({LimitUpdateFrequencyExceededException.class})
    public ResponseEntity<Object> handleLimitUpdateFrequencyExceededException(LimitUpdateFrequencyExceededException exception) {
        log.warn("LimitUpdateFrequencyExceededException: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }
}
