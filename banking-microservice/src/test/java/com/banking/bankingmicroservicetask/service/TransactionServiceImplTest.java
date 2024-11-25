package com.banking.bankingmicroservicetask.service;

import com.banking.bankingmicroservicetask.dao.BankAccountRepository;
import com.banking.bankingmicroservicetask.dao.TransactionRepository;
import com.banking.dto.TransactionDto;
import com.banking.bankingmicroservicetask.entity.BankAccount;
import com.banking.bankingmicroservicetask.entity.Transaction;
import com.banking.enums.TransactionCategory;
import com.banking.bankingmicroservicetask.exceptions.InvalidTransactionSumException;
import com.banking.bankingmicroservicetask.exceptions.NoSuchBankAccountException;
import com.banking.bankingmicroservicetask.mappers.TransactionMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.UUID;

@Disabled
@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private BankAccountRepository bankAccountRepository;
    @Mock
    private TransactionMapper transactionMapper;
    @InjectMocks
    private TransactionServiceImpl transactionService;


    private BankAccount bankAccount;
    private TransactionDto transactionDto;
    private Transaction transaction;
    private UUID id;

    @BeforeEach
    void init() {
        id = UUID.randomUUID();

        transactionDto = new TransactionDto();
        transactionDto.setBankAccountId(id);

        bankAccount = new BankAccount();
        bankAccount.setLimitServices(200.0);
        bankAccount.setAvailableFunds(1000.0);

        transaction = new Transaction();
    }

    @Test
    public void saveTransaction_ExpectNoSuchBankAccountException() {
        given(bankAccountRepository.findByIdAndDeletedTimeIsNull(id)).willReturn(Optional.empty());

        Assertions.assertThrows(NoSuchBankAccountException.class, () -> {
            transactionService.saveTransaction(transactionDto);
        });
    }

    @Test
    public void saveTransactionWithValidSum_ExpectSuccess() {
        transactionDto.setTransactionCategory(TransactionCategory.SERVICES);
        transactionDto.setTransactionSum(100.0);

        given(bankAccountRepository.findByIdAndDeletedTimeIsNull(transactionDto.getBankAccountId())).willReturn(Optional.of(bankAccount));
        given(transactionMapper.TransactionDtoToTransaction(transactionDto)).willReturn(new Transaction());

        transactionService.saveTransaction(transactionDto);

        Assertions.assertEquals(100.0, bankAccount.getLimitServices());
        Assertions.assertEquals(900.0, bankAccount.getAvailableFunds());
    }


    @Test
    public void saveTransactionWithInvalidSum_ExpectInvalidTransactionSumException() {
        transactionDto.setTransactionSum(-1.0);

        given(bankAccountRepository.findByIdAndDeletedTimeIsNull(transactionDto.getBankAccountId())).willReturn(Optional.of(bankAccount));

        Assertions.assertThrows(InvalidTransactionSumException.class, () -> {
            transactionService.saveTransaction(transactionDto);
        });
    }


    @Test
    public void saveTransactionExceedingLimit_ExpectTransactionSavedBankAccountFundsUnchanged() {
        transactionDto.setTransactionCategory(TransactionCategory.SERVICES);
        transactionDto.setTransactionSum(300.0);

        given(bankAccountRepository.findByIdAndDeletedTimeIsNull(transactionDto.getBankAccountId())).willReturn(Optional.of(bankAccount));
        given(transactionMapper.TransactionDtoToTransaction(transactionDto)).willReturn(transaction);

        transactionService.saveTransaction(transactionDto);

        verify(transactionRepository, times(1)).save(transaction);
        verify(bankAccountRepository, times(1)).save(bankAccount);
        Assertions.assertTrue(transaction.getLimitExceeded());
        Assertions.assertEquals(1000.0, bankAccount.getAvailableFunds());
    }

}

