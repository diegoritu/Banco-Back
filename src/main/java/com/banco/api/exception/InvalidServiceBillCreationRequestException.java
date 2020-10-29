package com.banco.api.exception;

public class InvalidServiceBillCreationRequestException extends RuntimeException {

    public InvalidServiceBillCreationRequestException(String message) {
        super(message);
    }

    public InvalidServiceBillCreationRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
