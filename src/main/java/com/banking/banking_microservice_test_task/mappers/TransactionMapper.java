package com.banking.banking_microservice_test_task.mappers;

import com.banking.banking_microservice_test_task.dto.TransactionDto;
import com.banking.banking_microservice_test_task.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(uses = CurrencyShortnameMapper.class)
public interface TransactionMapper {

    Transaction TransactionDtoToTransaction(TransactionDto dto);

    TransactionDto transactionToTransactionDto(Transaction entity);
}
