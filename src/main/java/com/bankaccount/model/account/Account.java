package com.bankaccount.model.account;

import com.bankaccount.model.operation.Operation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class Account {

    private final String id;
    private final List<Operation> operations;

    public Account(String id, List<Operation> operations) {
        this.id = id;
        this.operations = operations;
    }

    public String getId() {
        return id;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public Optional<Operation> getLastOperation() {
        return this.operations
                .stream()
                .reduce((operation, operation2) -> operation2);
    }

    public BigDecimal getBalance() {
        return this.getLastOperation()
                .map(Operation::getBalance)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
