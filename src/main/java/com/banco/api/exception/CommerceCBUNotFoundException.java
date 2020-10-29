package com.banco.api.exception;

public class CommerceCBUNotFoundException extends RuntimeException{
	public CommerceCBUNotFoundException(String message) {
        super(message);
    }

    public CommerceCBUNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}