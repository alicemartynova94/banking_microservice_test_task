package com.banking.bankingmicroservicetask.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
//TODO It's not possible to completely get rid of spring-boot-starter-web,
// because the current exception handling ExceptionHandler relies on Spring MVC
public class BankAccountGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchBankAccountException.class)
    protected ResponseEntity<Object> handleNoSuchBankAccountException(NoSuchBankAccountException exception) {
        log.error("Such bank account does not exist: {}", exception.getMessage());
        BankAccountIncorrectData bankAccountIncorrectData = new BankAccountIncorrectData();
        bankAccountIncorrectData.setInfo(exception.getMessage());
        return new ResponseEntity<>(bankAccountIncorrectData, HttpStatus.NOT_FOUND);
    }

}
