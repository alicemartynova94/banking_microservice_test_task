package com.banking.banking_microservice_test_task.mappers;


import com.banking.banking_microservice_test_task.dto.BankAccountDto;
import com.banking.banking_microservice_test_task.entity.BankAccount;
import org.mapstruct.Mapper;

@Mapper(uses = {CurrencyShortnameMapper.class, TransactionLimitMapper.class})
public interface BankAccountMapper {

    BankAccount bankAccountDtoToBankAccount(BankAccountDto dto);

    BankAccountDto bankAccountToBankAccountDto(BankAccount entity);

}
