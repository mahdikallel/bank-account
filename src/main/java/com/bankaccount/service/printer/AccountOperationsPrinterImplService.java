package com.bankaccount.service.printer;

import com.bankaccount.model.account.Account;
import com.bankaccount.service.printer.formater.OperationFormatterService;

import static com.bankaccount.service.printer.constant.Constant.*;

public class AccountOperationsPrinterImplService implements AccountOperationsPrinterService {

    private final OperationFormatterService operationFormatter;

    public AccountOperationsPrinterImplService(OperationFormatterService operationFormatter) {
        this.operationFormatter = operationFormatter;
    }

    @Override
    public String print(Account account) {
        return new StringBuilder(HEADER_ALIGN_FORMAT)
                .append(operationFormatter.apply(account.getOperations()))
                .toString();
    }
}
