package com.banking.banking_microservice_test_task.exception_handling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class BankAccountGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchBankAccountException.class)
    protected ResponseEntity<Object> handleNoSuchBankAccountException() {
        log.error("There is no bank account with such id");
        return new ResponseEntity<>(new BankAccountIncorrectData(), HttpStatus.NOT_FOUND);
    }

}
