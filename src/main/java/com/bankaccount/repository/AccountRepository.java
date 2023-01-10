package com.bankaccount.repository;

import com.bankaccount.model.account.Account;

import java.util.Optional;
import java.util.Set;

public interface AccountRepository {

    Set<Account> getAllAccount();

    Optional<Account> findAccountById(String accountId);

    void add(Account account);
}
