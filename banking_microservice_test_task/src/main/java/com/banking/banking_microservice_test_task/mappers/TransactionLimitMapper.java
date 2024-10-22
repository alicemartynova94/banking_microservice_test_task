package com.banking.banking_microservice_test_task.mappers;

import com.banking.banking_microservice_test_task.dto.TransactionLimitDto;
import com.banking.banking_microservice_test_task.entity.TransactionLimit;
import org.mapstruct.Mapper;

@Mapper(uses = CurrencyShortnameMapper.class)
public interface TransactionLimitMapper {
    TransactionLimit limitDtoToLimit(TransactionLimitDto dto);
    TransactionLimitDto limitToLimitDto(TransactionLimit entity);
}
