package com.bankaccount.exception;

public class AccountNotExistException extends RuntimeException {

    public AccountNotExistException(String message) {
        super(message);
    }
}
