package com.bankaccount.service.withdrawal;

import com.bankaccount.exception.AccountNotExistException;
import com.bankaccount.exception.NegativeAmountException;
import com.bankaccount.model.account.Account;

import java.math.BigDecimal;

public interface AccountWithdrawalService {
    void withdrawal(Account account, BigDecimal amount) throws NegativeAmountException, AccountNotExistException;
}
