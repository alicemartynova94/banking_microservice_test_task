package com.banking.banking_microservice_test_task.mappers;

import com.banking.banking_microservice_test_task.dto.CurrencyShortnameDto;
import com.banking.banking_microservice_test_task.entity.CurrencyShortname;
import org.mapstruct.Mapper;

@Mapper
public interface CurrencyShortnameMapper {

    CurrencyShortname currencyShortnameDtoToCurrencyShortname(CurrencyShortnameDto dto);

    CurrencyShortnameDto currencyShortnameToCurrencyShortnameDto(CurrencyShortname entity);
}
