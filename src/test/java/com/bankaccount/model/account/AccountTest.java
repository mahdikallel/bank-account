package com.bankaccount.model.account;

import com.bankaccount.model.operation.Operation;
import com.bankaccount.model.operation.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class AccountTest {

    private final LocalDateTime date = LocalDateTime.now();

    @Test
    public void should_return_account_last_operation() {
        // Given
        Account account = new Account("acount1", List.of(
                new Operation(Type.DEPOSIT, this.date, new BigDecimal("100"), new BigDecimal("100")),
                new Operation(Type.DEPOSIT, this.date.plusDays(1), new BigDecimal("100"), new BigDecimal("200")),
                new Operation(Type.DEPOSIT, this.date.plusDays(2), new BigDecimal("100"), new BigDecimal("300"))
        ));

        // When
        Optional<Operation> actualLastOperation = account.getLastOperation();


        //Then
        Operation expectedLastOperation = new Operation(Type.DEPOSIT, this.date.plusDays(2), new BigDecimal("100"), new BigDecimal("300"));

        Assertions.assertTrue(actualLastOperation.isPresent());
        Assertions.assertEquals(expectedLastOperation, actualLastOperation.get());

    }

    @Test
    public void should_return_init_account_balance_when_no_operation_exist() {
        // Given
        Account account = new Account("account1", new ArrayList<>());
        BigDecimal expectedBalance = BigDecimal.ZERO;

        // When
        BigDecimal actualBalance = account.getBalance();


        // Then
        Assertions.assertEquals(expectedBalance, actualBalance);
    }
}
