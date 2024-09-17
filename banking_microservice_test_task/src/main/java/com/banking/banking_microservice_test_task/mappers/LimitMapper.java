package com.banking.banking_microservice_test_task.mappers;

import com.banking.banking_microservice_test_task.dto.LimitDto;
import com.banking.banking_microservice_test_task.entity.Limit;
import org.mapstruct.Mapper;

@Mapper(uses = {CurrencyShortnameMapper.class, BankAccountMapper.class})
public interface LimitMapper {
    Limit limitDtoToLimit(LimitDto dto);
    LimitDto limitToLimitDto(Limit entity);
}
