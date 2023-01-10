package com.bankaccount.service;

import com.bankaccount.model.account.Account;
import com.bankaccount.service.deposit.AccountDepositService;
import com.bankaccount.service.withdrawal.AccountWithdrawalService;

import java.math.BigDecimal;

public class AccountImplService implements AccountService {

    private final AccountDepositService accountDepositService;
    private final AccountWithdrawalService accountWithdrawalService;

    public AccountImplService(AccountDepositService accountDepositService, AccountWithdrawalService accountWithdrawalService) {
        this.accountDepositService = accountDepositService;
        this.accountWithdrawalService = accountWithdrawalService;
    }

    @Override
    public void deposit(Account account, BigDecimal amount) {
        this.accountDepositService.deposit(account, amount);
    }

    @Override
    public void withdrawal(Account account, BigDecimal amount) {
        this.accountWithdrawalService.withdrawal(account, amount);
    }
}
