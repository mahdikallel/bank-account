package com.bankaccount.service.printer.formater;

import com.bankaccount.model.operation.Operation;
import com.bankaccount.model.operation.Type;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.bankaccount.service.printer.constant.Constant.*;

public class OperationFormatterService implements Function<List<Operation>,String> {

    @Override
    public String apply(List<Operation> operations) {
        return operations.stream()
                .sorted(Comparator.comparing(Operation::getDate))
                .map(this::formatOperation)
                .collect(Collectors.joining());
    }

    private String formatOperation(Operation operation) {
        return new StringBuilder()
                .append(System.lineSeparator())
                .append(String.format(COLUMNS_ALIGN_FORMAT, operation.getDate().format(OPERATION_DATE_FORMATTER), translateOperationType(operation), operation.getAmount(), operation.getBalance()))
                .toString();
    }

    private String translateOperationType(Operation operation) {
        if (operation.getType().equals(Type.DEPOSIT)) {
            return DEPOSIT_FR;
        }
        if (operation.getType().equals(Type.WITHDRAWAL)) {
            return WITHDRAWAL_FR;
        }
        return EMPTY;
    }
}
