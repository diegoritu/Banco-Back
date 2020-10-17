package com.banco.api.exception;

public class BusinessCBUNotFoundException extends RuntimeException{
	public BusinessCBUNotFoundException(String message) {
        super(message);
    }

    public BusinessCBUNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
