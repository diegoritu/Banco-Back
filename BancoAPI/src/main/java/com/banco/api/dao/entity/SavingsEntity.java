package com.banco.api.dao.entity;

import javax.persistence.*;

@Entity
@Table(name = "savings_accounts")
public class SavingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idSavingsAccount;

    private String accountNumber;
    private float balance;
    private String alias;
    private String cbu;
    private float interestRate;

    public SavingsEntity(int idSavingsAccount, String accountNumber, float balance, String alias, String cbu,
                         float interestRate) {
        super();
        this.idSavingsAccount = idSavingsAccount;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.alias = alias;
        this.cbu = cbu;
        this.interestRate = interestRate;
    }

    public int getIdSavingsAccount() {
        return idSavingsAccount;
    }

    public void setIdSavingsAccount(int idSavingsAccount) {
        this.idSavingsAccount = idSavingsAccount;
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

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }
}
