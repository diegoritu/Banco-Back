package com.banco.api.dto.account;

public abstract class AccountDTO {

    private int id;
    private String accountNumber;
    private float balance;
    private boolean active;
    private String cbu;
    private String accountType;

    public AccountDTO() {
    }

    public AccountDTO(int id, String accountNumber, float balance, boolean active, String cbu, String accountType) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.active = active;
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

    public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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
                ", cbu='" + cbu + '\'' +
                ", accountType='" + accountType + '\'' +
                ", active='" + active + '\'' +
                '}';
    }
}
