package com.banco.api.exception;

public class CheckingAccountRequestException extends RuntimeException {

    public CheckingAccountRequestException(String message) {
        super(message);
    }

    public CheckingAccountRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
