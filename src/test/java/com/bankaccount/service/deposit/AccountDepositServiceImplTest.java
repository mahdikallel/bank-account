package com.bankaccount.service.deposit;

import com.bankaccount.exception.AccountNotExistException;
import com.bankaccount.exception.NegativeAmountException;
import com.bankaccount.model.account.Account;
import com.bankaccount.model.operation.Operation;
import com.bankaccount.model.operation.Type;
import com.bankaccount.repository.AccountRepository;
import com.bankaccount.repository.inmemory.InMemoryAccountRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class AccountDepositServiceImplTest {

    private final LocalDateTime now = LocalDateTime.now();
    private AccountDepositService accountDepositService;
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        this.accountRepository = new InMemoryAccountRepositoryImpl(new HashSet<>());
        this.accountDepositService = new AccountDepositServiceImpl(now, accountRepository);
    }

    @Test
    public void should_create_one_deposit_operation_when_calling_deposit_method() throws NegativeAmountException, AccountNotExistException {
        // Given
        Account account = new Account("account1", new ArrayList<>());
        accountRepository.add(account);

        BigDecimal amountToDispose = new BigDecimal("100");
        int initialOperationsNumber = account.getOperations().size();

        // When
        accountDepositService.deposit(account, amountToDispose);

        // Then
        int expectedOperationsNumber = account.getOperations().size();
        Assertions.assertFalse(account.getOperations().isEmpty());
        Assertions.assertEquals(expectedOperationsNumber, initialOperationsNumber + 1);
    }

    @Test
    public void should_throw_exception_when_deposit_with_negative_amount() {
        // Given
        Account account = new Account("account1", new ArrayList<>());
        BigDecimal amount = new BigDecimal("-100");
        accountRepository.add(account);

        // When
        Exception negativeAmountException = Assertions.assertThrows(NegativeAmountException.class, () -> {
            accountDepositService.deposit(account, amount);
        });

        // Then
        Assertions.assertEquals(negativeAmountException.getMessage(), "Deposit Amount -100 cannot be negative");
    }

    @Test
    public void should_create_deposit_operation_when_calling_deposit_method() throws NegativeAmountException, AccountNotExistException {
        // Given
        Account account = new Account("account1", new ArrayList<>());
        BigDecimal amountToDeposit = new BigDecimal("100");
        BigDecimal newBalance = new BigDecimal("100");
        accountRepository.add(account);

        // When
        accountDepositService.deposit(account, amountToDeposit);

        // Then
        Operation expectedOperation = new Operation(Type.DEPOSIT, this.now, amountToDeposit, newBalance);

        Assertions.assertTrue(account.getLastOperation().isPresent());
        Assertions.assertEquals(account.getLastOperation().get(), expectedOperation);
    }

    @Test
    public void should_throw_exception_when_account_not_exist() {
        // Given
        Account account = new Account("account2", new ArrayList<>());
        BigDecimal amountToDeposit = new BigDecimal("100");

        // When

        AccountNotExistException accountNotExistException = Assertions.assertThrows(AccountNotExistException.class, () -> {
            accountDepositService.deposit(account, amountToDeposit);
        });

        Assertions.assertEquals(accountNotExistException.getMessage(), "Account with id account2 dose not exist");
    }

    @Test
    public void should_create_many_deposit_operations_when_calling_deposit_method() throws NegativeAmountException, AccountNotExistException {
        // Given
        Account account = new Account("account1", new ArrayList<>());
        accountRepository.add(account);

        BigDecimal firstAmountToDeposit = new BigDecimal("100");
        BigDecimal secondAmountToDeposit = new BigDecimal("54.1");
        BigDecimal thirdAmountToDeposit = new BigDecimal("57.2");
        BigDecimal expectedBalance = new BigDecimal("211.3");

        // When
        accountDepositService.deposit(account, firstAmountToDeposit);
        accountDepositService.deposit(account, secondAmountToDeposit);
        accountDepositService.deposit(account, thirdAmountToDeposit);

        // Then
        List<Operation> expectedOperation =  List.of(
                new Operation(Type.DEPOSIT, this.now, firstAmountToDeposit, firstAmountToDeposit),
                new Operation(Type.DEPOSIT, this.now, secondAmountToDeposit, firstAmountToDeposit.add(secondAmountToDeposit)),
                new Operation(Type.DEPOSIT, this.now, thirdAmountToDeposit, firstAmountToDeposit.add(secondAmountToDeposit).add(thirdAmountToDeposit))
        );

        Assertions.assertEquals(account.getOperations(), expectedOperation);
        Assertions.assertEquals(account.getLastOperation().get().getBalance(), expectedBalance);
    }

}
