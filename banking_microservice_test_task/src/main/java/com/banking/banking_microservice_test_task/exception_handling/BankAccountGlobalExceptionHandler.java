package com.banking.banking_microservice_test_task.exception_handling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BankAccountGlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(BankAccountGlobalExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<BankAccountIncorrectData> handleException(NoSuchBankAccountException exception) {
        logger.error("Such bank account does not exist: {}", exception.getMessage());
        BankAccountIncorrectData bankAccountIncorrectData = new BankAccountIncorrectData();
        bankAccountIncorrectData.setInfo(exception.getMessage());
        return new ResponseEntity<>(bankAccountIncorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<BankAccountIncorrectData> handleException(Exception exception) {
        logger.error("An error occurred: {}", exception.getMessage());
        BankAccountIncorrectData bankAccountIncorrectData = new BankAccountIncorrectData();
        bankAccountIncorrectData.setInfo(exception.getMessage());
        return new ResponseEntity<>(bankAccountIncorrectData, HttpStatus.BAD_REQUEST);
    }
}
