package com.banco.api.exception;

public class AccountCBUNotFoundException extends RuntimeException {

    public AccountCBUNotFoundException(String message) {
        super(message);
    }

    public AccountCBUNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
