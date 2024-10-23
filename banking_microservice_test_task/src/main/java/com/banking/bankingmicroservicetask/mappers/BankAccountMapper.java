package com.banking.bankingmicroservicetask.mappers;


import com.banking.bankingmicroservicetask.dto.BankAccountDto;
import com.banking.bankingmicroservicetask.entity.BankAccount;
import org.mapstruct.Mapper;

@Mapper(uses = {CurrencyShortnameMapper.class, TransactionLimitMapper.class, TransactionMapper.class})
public interface BankAccountMapper {

    BankAccount bankAccountDtoToBankAccount(BankAccountDto dto);

    BankAccountDto bankAccountToBankAccountDto(BankAccount entity);

}
