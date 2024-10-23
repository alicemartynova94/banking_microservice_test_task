package com.banking.bankingmicroservicetask.mappers;

import com.banking.bankingmicroservicetask.dto.TransactionDto;
import com.banking.bankingmicroservicetask.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(uses = CurrencyShortnameMapper.class)
public interface TransactionMapper {

    Transaction TransactionDtoToTransaction(TransactionDto dto);

    TransactionDto transactionToTransactionDto(Transaction entity);
}
