package com.banco.api.exception;

public class ClientInsuficientFundsException extends RuntimeException{
	public ClientInsuficientFundsException(String message) {
        super(message);
    }

    public ClientInsuficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}