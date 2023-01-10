package com.bankaccount.exception;

public class AccountNotExistException extends  Exception{

    public AccountNotExistException(String message) {
        super(message);
    }
}
