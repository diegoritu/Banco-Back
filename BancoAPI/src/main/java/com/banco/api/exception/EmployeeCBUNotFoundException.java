package com.banco.api.exception;

public class EmployeeCBUNotFoundException extends RuntimeException {

    public EmployeeCBUNotFoundException(String message) {
        super(message);
    }

    public EmployeeCBUNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
