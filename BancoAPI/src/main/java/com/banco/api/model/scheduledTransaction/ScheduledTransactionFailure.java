package com.banco.api.model.scheduledTransaction;

public class ScheduledTransactionFailure {

    private String errorCode;
    private String message;

    public ScheduledTransactionFailure() {
    }

    public ScheduledTransactionFailure(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
