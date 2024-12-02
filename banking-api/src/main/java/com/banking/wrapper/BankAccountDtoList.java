package com.banking.wrapper;

import com.banking.dto.BankAccountDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class BankAccountDtoList {
    private List<BankAccountDto> accountDtos;
}
