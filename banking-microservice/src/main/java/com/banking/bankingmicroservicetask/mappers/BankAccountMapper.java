package com.banking.bankingmicroservicetask.mappers;

import com.banking.bankingmicroservicetask.entity.BankAccount;
import com.banking.dto.BankAccountDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {

    BankAccount bankAccountDtoToBankAccount(BankAccountDto dto);

    BankAccountDto bankAccountToBankAccountDto(BankAccount entity);

}
