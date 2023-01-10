package com.bankaccount.repository.inmemory;

import com.bankaccount.model.account.Account;
import com.bankaccount.repository.AccountRepository;

import java.util.Optional;
import java.util.Set;

public class InMemoryAccountRepositoryImpl implements AccountRepository {

    private final Set<Account> accounts;

    public InMemoryAccountRepositoryImpl(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public Set<Account> getAllAccount() {
        return this.accounts;
    }

    @Override
    public Optional<Account> findAccountById(String accountId) {
        return this.accounts
                .stream()
                .filter(account -> account.getId().equals(accountId))
                .findFirst();
    }

    @Override
    public void add(Account account) {
        this.accounts.add(account);
    }
}
