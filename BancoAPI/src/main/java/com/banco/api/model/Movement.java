package com.banco.api.model;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

public class Movement {
    private int idMovement;
    private MovementType movementType;
    private LocalDateTime dayAndHour;
    private String concept;
    private float amount;
    private int transactionNumber;
    private Service service;
    private String reference;
    private float entryBalanceBeforeMovement;
    private float exitBalanceBeforeMovement;
    private Checking chEntryAccount;
    private Checking chExitAccount;
    private Savings saEntryAccount;
    private Savings saExitAccount;

    /*
     * Constructor without idMovement
     */
    public Movement(MovementType movementType, LocalDateTime dayAndHour, String concept, float amount,
                    int transactionNumber, Service service, String reference, float entryBalanceBeforeMovement,
                    float exitBalanceBeforeMovement, Checking chEntryAccount, Checking chExitAccount, Savings saEntryAccount,
                    Savings saExitAccount) {
        super();
        this.movementType = movementType;
        this.dayAndHour = dayAndHour;
        this.concept = concept;
        this.amount = amount;
        this.transactionNumber = transactionNumber;
        this.service = service;
        this.reference = reference;
        this.entryBalanceBeforeMovement = entryBalanceBeforeMovement;
        this.exitBalanceBeforeMovement = exitBalanceBeforeMovement;
        this.chEntryAccount = chEntryAccount;
        this.chExitAccount = chExitAccount;
        this.saEntryAccount = saEntryAccount;
        this.saExitAccount = saExitAccount;
    }

    /*
     * Constructor with idMovement
     */

    public Movement(int idMovement, MovementType movementType, LocalDateTime dayAndHour, String concept, float amount,
                    int transactionNumber, Service service, String reference, float entryBalanceBeforeMovement,
                    float exitBalanceBeforeMovement, Checking chEntryAccount, Checking chExitAccount, Savings saEntryAccount,
                    Savings saExitAccount) {
        super();
        this.idMovement = idMovement;
        this.movementType = movementType;
        this.dayAndHour = dayAndHour;
        this.concept = concept;
        this.amount = amount;
        this.transactionNumber = transactionNumber;
        this.service = service;
        this.reference = reference;
        this.entryBalanceBeforeMovement = entryBalanceBeforeMovement;
        this.exitBalanceBeforeMovement = exitBalanceBeforeMovement;
        this.chEntryAccount = chEntryAccount;
        this.chExitAccount = chExitAccount;
        this.saEntryAccount = saEntryAccount;
        this.saExitAccount = saExitAccount;
    }


    public int getIdMovement() {
        return idMovement;
    }

    public void setIdMovement(int idMovement) {
        this.idMovement = idMovement;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public LocalDateTime getDayAndHour() {
        return dayAndHour;
    }

    public void setDayAndHour(LocalDateTime dayAndHour) {
        this.dayAndHour = dayAndHour;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(int transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public float getEntryBalanceBeforeMovement() {
        return entryBalanceBeforeMovement;
    }

    public void setEntryBalanceBeforeMovement(float entryBalanceBeforeMovement) {
        this.entryBalanceBeforeMovement = entryBalanceBeforeMovement;
    }

    public float getExitBalanceBeforeMovement() {
        return exitBalanceBeforeMovement;
    }

    public void setExitBalanceBeforeMovement(float exitBalanceBeforeMovement) {
        this.exitBalanceBeforeMovement = exitBalanceBeforeMovement;
    }

    public Checking getChEntryAccount() {
        return chEntryAccount;
    }

    public void setChEntryAccount(Checking chEntryAccount) {
        this.chEntryAccount = chEntryAccount;
    }

    public Checking getChExitAccount() {
        return chExitAccount;
    }

    public void setChExitAccount(Checking chExitAccount) {
        this.chExitAccount = chExitAccount;
    }

    public Savings getSaEntryAccount() {
        return saEntryAccount;
    }

    public void setSaEntryAccount(Savings saEntryAccount) {
        this.saEntryAccount = saEntryAccount;
    }

    public Savings getSaExitAccount() {
        return saExitAccount;
    }

    public void setSaExitAccount(Savings saExitAccount) {
        this.saExitAccount = saExitAccount;
    }

}
