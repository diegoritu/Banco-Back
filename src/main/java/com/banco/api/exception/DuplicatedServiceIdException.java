package com.banco.api.exception;

public class DuplicatedServiceIdException extends RuntimeException {
    public DuplicatedServiceIdException(String message) {
        super(message);
    }

    public DuplicatedServiceIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
