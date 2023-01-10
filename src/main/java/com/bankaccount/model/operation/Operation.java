package com.bankaccount.model.operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Operation {

    private final Type type;

    private final LocalDateTime date;

    private final BigDecimal amount;

    private final BigDecimal balance;

    public Operation(Type type, LocalDateTime date, BigDecimal amount, BigDecimal balance) {
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.balance = balance;
    }

    public Type getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return type == operation.type &&
                date.equals(operation.date) &&
                amount.equals(operation.amount) &&
                balance.equals(operation.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, date, amount, balance);
    }

    @Override
    public String toString() {
        return "Operation{" +
                "type=" + type +
                ", date=" + date +
                ", amount=" + amount +
                ", balance=" + balance +
                '}';
    }
}
