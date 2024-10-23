package com.banking.bankingmicroservicetask.mappers;

import com.banking.bankingmicroservicetask.dto.CurrencyShortnameDto;
import com.banking.bankingmicroservicetask.entity.CurrencyShortname;
import org.mapstruct.Mapper;

@Mapper
public interface CurrencyShortnameMapper {

    CurrencyShortname currencyShortnameDtoToCurrencyShortname(CurrencyShortnameDto dto);

    CurrencyShortnameDto currencyShortnameToCurrencyShortnameDto(CurrencyShortname entity);
}
