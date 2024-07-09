package com.banking.banking_microservice_test_task.exception_handling;

import com.banking.banking_microservice_test_task.entity.BankAccount;
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
    protected ResponseEntity<Object> handleNoSuchBankAccountException(NoSuchBankAccountException exception) {
        log.error("Such bank account does not exist: {}", exception.getMessage());
        BankAccountIncorrectData bankAccountIncorrectData = new BankAccountIncorrectData();
        bankAccountIncorrectData.setInfo(exception.getMessage());
        return new ResponseEntity<>(bankAccountIncorrectData, HttpStatus.NOT_FOUND);
    }

}
