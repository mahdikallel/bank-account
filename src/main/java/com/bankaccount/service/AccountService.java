package com.bankaccount.service;

import com.bankaccount.model.account.Account;

import java.math.BigDecimal;

public interface AccountService {
    void deposit(Account account, BigDecimal amount);

    void withdrawal(Account account, BigDecimal amount);
}
