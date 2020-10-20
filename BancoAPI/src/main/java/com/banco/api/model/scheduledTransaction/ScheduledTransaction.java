package com.banco.api.model.scheduledTransaction;

import java.util.Date;

public abstract class ScheduledTransaction {

    protected Date scheduledDate;
    protected ScheduledTransactionStatus status;
    protected ScheduledTransactionFailure failure;

    public ScheduledTransaction() {
    }

    public ScheduledTransaction(Date scheduledDate, ScheduledTransactionStatus status) {
        this.scheduledDate = scheduledDate;
        this.status = status;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public ScheduledTransactionStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduledTransactionStatus status) {
        this.status = status;
    }

    public ScheduledTransactionFailure getFailure() {
        return failure;
    }

    public void setFailure(String errorCode, String message) {
        this.failure = new ScheduledTransactionFailure(errorCode, message);
    }
}
