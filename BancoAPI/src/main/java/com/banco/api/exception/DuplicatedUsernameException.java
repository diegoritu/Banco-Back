package com.banco.api.exception;

public class DuplicatedUsernameException extends RuntimeException {

    public DuplicatedUsernameException(String message) {
        super(message);
    }

    public DuplicatedUsernameException(String message, Throwable cause) {
        super(message, cause);
    }
}
