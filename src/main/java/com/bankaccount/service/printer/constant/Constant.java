package com.bankaccount.service.printer.constant;

import java.time.format.DateTimeFormatter;

public class Constant {
    public static final String COLUMNS_ALIGN_FORMAT = "%-20.20s |%-20.20s |%10.10s |%10.10s |";
    public static final String HEADER_ALIGN_FORMAT = String.format(COLUMNS_ALIGN_FORMAT, "Date d'opération", "Typde d'opération", "Montant", "Balance");
    public static final DateTimeFormatter OPERATION_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String DEPOSIT_FR = "Depôt";
    public static final String WITHDRAWAL_FR = "Retrait";
    public static final String EMPTY = "";
}
