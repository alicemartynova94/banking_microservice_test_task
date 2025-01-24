package com.banking.bankingmicroservicetask.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.banking.bankingmicroservicetask.dao.BankAccountRepository;
import com.banking.bankingmicroservicetask.dao.TransactionRepository;
import com.banking.bankingmicroservicetask.entity.BankAccount;
import com.banking.bankingmicroservicetask.entity.Transaction;
import com.banking.bankingmicroservicetask.exceptions.InvalidTransactionSumException;
import com.banking.bankingmicroservicetask.exceptions.NoSuchBankAccountException;
import com.banking.bankingmicroservicetask.mappers.TransactionMapper;
import com.banking.dto.TransactionDto;
import com.banking.enums.TransactionCategory;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

  @Mock
  private Map<String, TransactionCategoryStrategy> strategies;
  @Mock
  private TransactionalOperator transactionalOperator;
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

    lenient().when(transactionalOperator.transactional((Mono<Object>) any()))
        .thenAnswer(invocation -> invocation.getArgument(0));
    lenient().when(bankAccountRepository.findByIdAndBankAccountDeletedTimeIsNull(transactionDto.getBankAccountId()))
        .thenReturn(Mono.just(bankAccount));
    lenient().when(transactionMapper.TransactionDtoToTransaction(transactionDto))
        .thenReturn(transaction);
    lenient().when(transactionRepository.save(transaction))
        .thenReturn(Mono.just(transaction));
    lenient().when(bankAccountRepository.save(bankAccount))
        .thenReturn(Mono.just(bankAccount));
  }

  @Test
  void saveTransaction_ExpectNoSuchBankAccountException() {
    transactionDto.setTransactionSum(100.0);

    given(bankAccountRepository.findByIdAndBankAccountDeletedTimeIsNull(id)).willReturn(Mono.empty());

    StepVerifier.create(transactionService.saveTransaction(transactionDto))
        .expectError(NoSuchBankAccountException.class)
        .verify();
  }

  @Test
  void saveTransactionWithValidSum_ExpectSuccess() {
    transactionDto.setTransactionCategory(TransactionCategory.SERVICES);
    transactionDto.setTransactionSum(100.0);
    ServicesCategory servicesCategory = new ServicesCategory(transactionRepository, bankAccountRepository);

    given(strategies.get(transactionDto.getTransactionCategory().name())).willReturn(servicesCategory);

    StepVerifier.create(transactionService.saveTransaction(transactionDto))
        .expectComplete()
        .verify();

    Assertions.assertEquals(100.0, bankAccount.getLimitServices());
    Assertions.assertEquals(900.0, bankAccount.getAvailableFunds());

    verify(transactionRepository, times(1)).save(transaction);
    verify(bankAccountRepository, times(1)).save(bankAccount);
  }

  @Test
  void saveTransactionWithInvalidSum_ExpectInvalidTransactionSumException() {
    transactionDto.setTransactionSum(-1.0);
    StepVerifier.create(transactionService.saveTransaction(transactionDto))
        .expectError(InvalidTransactionSumException.class)
        .verify();
  }

    @Test
    void saveTransactionExceedingLimit_ExpectTransactionSavedBankAccountFundsUnchanged() {
        transactionDto.setTransactionCategory(TransactionCategory.SERVICES);
        transactionDto.setTransactionSum(300.0);

    ServicesCategory servicesCategory = new ServicesCategory(transactionRepository, bankAccountRepository);

    given(strategies.get(transactionDto.getTransactionCategory().name())).willReturn(servicesCategory);
    given(servicesCategory.saveTransaction(bankAccount, transaction, transactionDto.getTransactionSum()))
        .willAnswer(invocation -> {
          transaction.setLimitExceeded(true);
          return Mono.empty();
        });

    StepVerifier.create(transactionService.saveTransaction(transactionDto))
        .expectComplete()
        .verify();

        verify(transactionRepository, times(1)).save(transaction);
        verify(bankAccountRepository, times(1)).save(bankAccount);
        Assertions.assertTrue(transaction.getLimitExceeded());
        Assertions.assertEquals(1000.0, bankAccount.getAvailableFunds());
    }

}

