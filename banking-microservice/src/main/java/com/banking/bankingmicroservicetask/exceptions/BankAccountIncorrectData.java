package com.banking.bankingmicroservicetask.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BankAccountIncorrectData {
    private String info;
}
