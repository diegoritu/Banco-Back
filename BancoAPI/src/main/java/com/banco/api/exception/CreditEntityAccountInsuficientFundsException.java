package com.banco.api.exception;

public class CreditEntityAccountInsuficientFundsException extends RuntimeException{

	public CreditEntityAccountInsuficientFundsException(String message) {
        super(message);
    }

    public CreditEntityAccountInsuficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}