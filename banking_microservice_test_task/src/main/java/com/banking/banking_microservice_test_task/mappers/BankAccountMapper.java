package com.banking.banking_microservice_test_task.mappers;


import com.banking.banking_microservice_test_task.dto.BankAccountDto;
import com.banking.banking_microservice_test_task.entity.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BankAccountMapper {

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "accountHolder", source = "dto.accountHolder")
    @Mapping(target = "availableFunds", source = "dto.availableFunds")
    @Mapping(target = "currencyShortname", source = "dto.currencyShortnameDto")
    BankAccount bankAccountDtoToBankAccount(BankAccountDto dto);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "accountHolder", source = "entity.accountHolder")
    @Mapping(target = "availableFunds", source = "entity.availableFunds")
    @Mapping(target = "currencyShortnameDto", source = "entity.currencyShortname")
    BankAccountDto bankAccountToBankAccountDto(BankAccount entity);

}
