package com.bankaccount.exception;

public class NegativeAmountException extends RuntimeException {

    public NegativeAmountException(String message) {
        super(message);
    }
}
