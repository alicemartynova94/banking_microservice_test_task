package com.banking.bankingmicroservicetask.mappers;

import com.banking.bankingmicroservicetask.dto.TransactionLimitDto;
import com.banking.bankingmicroservicetask.entity.TransactionLimit;
import org.mapstruct.Mapper;

@Mapper(uses = CurrencyShortnameMapper.class)
public interface TransactionLimitMapper {
    TransactionLimit limitDtoToLimit(TransactionLimitDto dto);
    TransactionLimitDto limitToLimitDto(TransactionLimit entity);
}
