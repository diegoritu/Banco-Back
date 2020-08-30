package com.banco.api.dto.account;

public abstract class AccountDTO {

    private int id;
    private String accountNumber;
    private float balance;
    private String alias;
    private String cbu;
    private String accountType;

    public AccountDTO() {
    }

    public AccountDTO(int id, String accountNumber, float balance, String alias, String cbu, String accountType) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.alias = alias;
        this.cbu = cbu;
        this.accountType = accountType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", alias='" + alias + '\'' +
                ", cbu='" + cbu + '\'' +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
