package com.bankaccount.service;

import com.bankaccount.model.account.Account;
import com.bankaccount.model.operation.Operation;
import com.bankaccount.model.operation.Type;
import com.bankaccount.repository.AccountRepository;
import com.bankaccount.repository.inmemory.InMemoryAccountRepositoryImpl;
import com.bankaccount.service.deposit.AccountDepositService;
import com.bankaccount.service.deposit.AccountDepositServiceImpl;
import com.bankaccount.service.withdrawal.AccountWithdrawalImplService;
import com.bankaccount.service.withdrawal.AccountWithdrawalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class AccountImplServiceTest {

    public static final String ACCOUNT_1 = "account1";
    private final LocalDateTime now = LocalDateTime.now();

    private AccountService accountService;
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        this.accountRepository = new InMemoryAccountRepositoryImpl(Set.of(new Account(ACCOUNT_1, new ArrayList<>())));
        AccountDepositService accountDepositService = new AccountDepositServiceImpl(this.now, this.accountRepository);
        AccountWithdrawalService accountWithdrawalService = new AccountWithdrawalImplService(this.now, this.accountRepository);
        this.accountService = new AccountImplService(accountDepositService, accountWithdrawalService);
    }

    @Test
    public void should_deposit_and_withdrawal() {

        // Given
        Account account = accountRepository.findAccountById(ACCOUNT_1).get();

        // When
        this.accountService.deposit(account, new BigDecimal("100.55"));
        this.accountService.deposit(account, new BigDecimal("15.57"));
        this.accountService.withdrawal(account, new BigDecimal("16"));
        this.accountService.deposit(account, new BigDecimal("200.16"));
        this.accountService.withdrawal(account, new BigDecimal("100.19"));

        //Then
        Assertions.assertEquals(List.of(
                new Operation(Type.DEPOSIT, this.now, new BigDecimal("100.55"), new BigDecimal("100.55")),
                new Operation(Type.DEPOSIT, this.now, new BigDecimal("15.57"), new BigDecimal("116.12")),
                new Operation(Type.WITHDRAWAL, this.now, new BigDecimal("16"), new BigDecimal("100.12")),
                new Operation(Type.DEPOSIT, this.now, new BigDecimal("200.16"), new BigDecimal("300.28")),
                new Operation(Type.WITHDRAWAL, this.now, new BigDecimal("100.19"), new BigDecimal("200.09"))
        ), account.getOperations());

        Operation expectedLastOperation = new Operation(Type.WITHDRAWAL, this.now, new BigDecimal("100.19"), new BigDecimal("200.09"));
        Assertions.assertEquals(expectedLastOperation,account.getLastOperation().get());
        Assertions.assertEquals(account.getBalance(),new BigDecimal("200.09"));
    }
}
