package com.banking.banking_microservice_test_task.exception_handling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class TransactionGlobalHandler {
    @ExceptionHandler({InvalidTransactionSumException.class})
    public ResponseEntity<Object> handleInvalidTransactionSumException(InvalidTransactionSumException exception) {
        log.error("InvalidTransactionSumException: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({InvalidTransactionTypeException.class})
    public ResponseEntity<Object> handleInvalidTransactionTypeException(InvalidTransactionTypeException exception) {
        log.error("InvalidTransactionTypeException: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

}
