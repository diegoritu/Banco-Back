package com.banco.api.model;

import java.util.TreeMap;

import org.springframework.stereotype.Component;

public abstract class Account {
    private String accountNumber;
    private float balance; //Saldo
    private String alias;
    private TreeMap<Movement, Float> movements;
    private String cbu;

    public Account(String accountNumber, float balance, String alias, TreeMap<Movement, Float> movements, String cbu) {
        super();
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.alias = alias;
        this.movements = movements;
        this.cbu = cbu;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public TreeMap<Movement, Float> getMovements() {
        return movements;
    }

    public void setMovements(TreeMap<Movement, Float> movements) {
        this.movements = movements;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }


    public abstract void deposit();

    public abstract void extract();

}
