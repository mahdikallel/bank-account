package com.bankaccount.service.withdrawal;

import com.bankaccount.exception.AccountNotExistException;
import com.bankaccount.exception.InsufficientBalanceException;
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

class AccountWithdrawalImplServiceTest {

    private final LocalDateTime now = LocalDateTime.now();
    private AccountWithdrawalService accountWithdrawalService;
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        this.accountRepository = new InMemoryAccountRepositoryImpl(new HashSet<>());
        this.accountWithdrawalService = new AccountWithdrawalImplService(this.now, accountRepository);
    }

    @Test
    public void should_create_withdrawal_operation_when_calling_withdrawal_method() throws NegativeAmountException, AccountNotExistException, InsufficientBalanceException {
        // Given

        Account account = new Account("account1", new ArrayList<>());

        account.getOperations().add(
                new Operation(Type.DEPOSIT, this.now, new BigDecimal("100"), new BigDecimal("100"))
        );
        accountRepository.add(account);

        Operation expectedOperation = new Operation(Type.WITHDRAWAL, this.now, new BigDecimal("100"), new BigDecimal("0"));

        //When
        accountWithdrawalService.withdrawal(account, new BigDecimal("100"));

        //Then
        Assertions.assertTrue(account.getLastOperation().isPresent());
        Assertions.assertEquals(account.getLastOperation().get(), expectedOperation);
    }

    @Test
    public void should_throw_exception_when_withdrawal_with_negative_amount() {
        // Given
        Account account = new Account("account1", new ArrayList<>());
        BigDecimal amount = new BigDecimal("-100");
        accountRepository.add(account);
        // When
        Exception negativeAmountException = Assertions.assertThrows(NegativeAmountException.class, () -> {
            accountWithdrawalService.withdrawal(account, amount);
        });

        // Then
        Assertions.assertEquals(negativeAmountException.getMessage(), "Withdrawal Amount -100 cannot be negative");
    }

    @Test
    public void should_throw_exception_when_account_not_exist() {
        // Given
        Account account = new Account("account1", new ArrayList<>());
        BigDecimal amount = new BigDecimal("100");

        // When
        Exception accountNotExistException = Assertions.assertThrows(AccountNotExistException.class, () -> {
            accountWithdrawalService.withdrawal(account, amount);
        });

        // Then
        Assertions.assertEquals(accountNotExistException.getMessage(), "Account with id account1 dose not exist");
    }


    @Test
    public void should_throw_exception_when_insufficient_balance() {
        // Given

        Account account = new Account("account1", new ArrayList<>());

        account.getOperations().add(
                new Operation(Type.DEPOSIT, this.now, new BigDecimal("100"), new BigDecimal("100"))
        );
        accountRepository.add(account);


        //When
        Exception insufficientBalanceException = Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            accountWithdrawalService.withdrawal(account, new BigDecimal("110"));
        });

        //Then
        Assertions.assertEquals(insufficientBalanceException.getMessage(), "Insufficient balance!");
    }


}
