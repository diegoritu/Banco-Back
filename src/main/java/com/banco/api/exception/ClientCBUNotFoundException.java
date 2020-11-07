package com.banco.api.exception;

public class ClientCBUNotFoundException extends RuntimeException {

    public ClientCBUNotFoundException(String message) {
        super(message);
    }

    public ClientCBUNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
