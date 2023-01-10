package com.bankaccount.service.deposit;

import com.bankaccount.exception.AccountNotExistException;
import com.bankaccount.exception.NegativeAmountException;
import com.bankaccount.model.account.Account;

import java.math.BigDecimal;

public interface AccountDepositService {
    void deposit(Account account, BigDecimal amount) throws NegativeAmountException, AccountNotExistException;
}
