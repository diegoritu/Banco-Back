package com.banco.api.model.scheduledTransaction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance
public abstract class ScheduledTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Temporal(TemporalType.DATE)
    protected Date scheduledDate;
    protected int status;
    protected String failureCode;
    protected String failureMessage;

    public int getId() {
        return id;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public ScheduledTransactionStatus getStatus() {
        return ScheduledTransactionStatus.from(this.status);
    }

    public void setStatus(ScheduledTransactionStatus status) {
        this.status = status.getValue();
    }

    public String getFailureCode() {
        return failureCode;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }
}
