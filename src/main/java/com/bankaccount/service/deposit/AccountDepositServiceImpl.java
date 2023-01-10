package com.bankaccount.service.deposit;

import com.bankaccount.exception.AccountNotExistException;
import com.bankaccount.exception.NegativeAmountException;
import com.bankaccount.model.account.Account;
import com.bankaccount.model.operation.Operation;
import com.bankaccount.model.operation.Type;
import com.bankaccount.repository.AccountRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountDepositServiceImpl implements AccountDepositService {

    private final LocalDateTime localDateTime;
    private final AccountRepository accountRepository;

    public AccountDepositServiceImpl(LocalDateTime localDateTime, AccountRepository accountRepository) {
        this.localDateTime = localDateTime;
        this.accountRepository = accountRepository;
    }

    @Override
    public void deposit(Account account, BigDecimal amount) throws NegativeAmountException, AccountNotExistException {

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeAmountException(String.format("Deposit Amount %s cannot be negative", amount));
        }
        if (accountRepository.findAccountById(account.getId()).isPresent()) {
            BigDecimal balance = account.getBalance();


            account.getOperations()
                    .add(new Operation(Type.DEPOSIT, this.localDateTime, amount, amount.add(balance)));
        } else {
            throw new AccountNotExistException(String.format("Account with id %s dose not exist", account.getId()));
        }
    }
}
