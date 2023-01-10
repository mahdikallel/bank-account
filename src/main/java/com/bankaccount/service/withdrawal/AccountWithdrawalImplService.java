package com.bankaccount.service.withdrawal;

import com.bankaccount.exception.AccountNotExistException;
import com.bankaccount.exception.InsufficientBalanceException;
import com.bankaccount.exception.NegativeAmountException;
import com.bankaccount.model.account.Account;
import com.bankaccount.model.operation.Operation;
import com.bankaccount.model.operation.Type;
import com.bankaccount.repository.AccountRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountWithdrawalImplService implements AccountWithdrawalService {

    private final LocalDateTime localDateTime;
    private final AccountRepository accountRepository;

    public AccountWithdrawalImplService(LocalDateTime localDateTime, AccountRepository accountRepository) {
        this.localDateTime = localDateTime;
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawal(Account account, BigDecimal amount) throws NegativeAmountException, AccountNotExistException, InsufficientBalanceException {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeAmountException(String.format("Withdrawal Amount %s cannot be negative", amount));
        }
        if (accountRepository.findAccountById(account.getId()).isPresent()) {
            if (account.getBalance().compareTo(amount) < 0) {
                throw new InsufficientBalanceException("Insufficient balance!");
            }
            BigDecimal newBalance = account.getBalance().subtract(amount);
            Operation operation = new Operation(Type.WITHDRAWAL, this.localDateTime, amount, newBalance);
            account.getOperations().add(operation);
        } else {
            throw new AccountNotExistException(String.format("Account with id %s dose not exist", account.getId()));
        }
    }
}
