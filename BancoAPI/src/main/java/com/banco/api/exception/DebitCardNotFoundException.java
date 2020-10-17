package com.banco.api.exception;

public class DebitCardNotFoundException extends RuntimeException{

	public DebitCardNotFoundException(String message) {
        super(message);
    }

    public DebitCardNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
