package com.bankaccount.service.printer;

import com.bankaccount.model.account.Account;
import com.bankaccount.model.operation.Operation;
import com.bankaccount.model.operation.Type;
import com.bankaccount.service.printer.formater.OperationFormatterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

class AccountOperationsPrinterImplServiceTest {

    private final LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2023, 1, 1), LocalTime.of(10, 10, 10));

    private AccountOperationsPrinterService accountOperationsPrinterService;

    @BeforeEach
    public void setup() {
        this.accountOperationsPrinterService = new AccountOperationsPrinterImplService(new OperationFormatterService());
    }

    @Test
    public void should_print_account_operation() {

        // Given
        Account account = new Account("account1", List.of(
                new Operation(Type.DEPOSIT, this.localDateTime, new BigDecimal("100.55"), new BigDecimal("100.55")),
                new Operation(Type.DEPOSIT, this.localDateTime.plusHours(4), new BigDecimal("15.57"), new BigDecimal("116.12")),
                new Operation(Type.WITHDRAWAL, this.localDateTime.plusDays(4), new BigDecimal("16"), new BigDecimal("100.12")),
                new Operation(Type.DEPOSIT, this.localDateTime.plusDays(5), new BigDecimal("200.16"), new BigDecimal("300.28")),
                new Operation(Type.WITHDRAWAL, this.localDateTime.plusDays(5).plusHours(2), new BigDecimal("100.19"), new BigDecimal("200.09"))
        ));

        // When
        String print = accountOperationsPrinterService.print(account);


        // Then
        String expected =
                "Date d'opération     |Typde d'opération    |   Montant |   Balance |" + System.lineSeparator() +
                        "2023-01-01 10:10:10  |Depôt                |    100.55 |    100.55 |" + System.lineSeparator() +
                        "2023-01-01 14:10:10  |Depôt                |     15.57 |    116.12 |" + System.lineSeparator() +
                        "2023-01-05 10:10:10  |Retrait              |        16 |    100.12 |" + System.lineSeparator() +
                        "2023-01-06 10:10:10  |Depôt                |    200.16 |    300.28 |" + System.lineSeparator() +
                        "2023-01-06 12:10:10  |Retrait              |    100.19 |    200.09 |";
        Assertions.assertEquals(expected, print);
    }
}
