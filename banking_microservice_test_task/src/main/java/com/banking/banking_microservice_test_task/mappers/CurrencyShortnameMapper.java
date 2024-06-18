package com.banking.banking_microservice_test_task.mappers;

import com.banking.banking_microservice_test_task.dto.CurrencyShortnameDto;
import com.banking.banking_microservice_test_task.entity.CurrencyShortname;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CurrencyShortnameMapper {
    //@Mapping(target = "id", source = "dto.id")
    //@Mapping(target = "currencyNameNumeric", source = "dto.currencyNameNumeric")
    CurrencyShortname currencyShortnameDtoToCurrencyShortname(CurrencyShortnameDto dto);

   // @Mapping(target = "id", source = "entity.id")
   // @Mapping(target = "currencyNameNumeric", source = "entity.currencyNameNumeric")
    CurrencyShortnameDto currencyShortnameToCurrencyShortnameDto(CurrencyShortname entity);
}
