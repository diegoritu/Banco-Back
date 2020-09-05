package com.banco.api.dto.account.request;

import java.io.Serializable;

public class AccountRequest implements Serializable{
	
	private String accountNumber;
    private float balance;
    private boolean active;
    private String cbu;
    private int accountType;
    
    public AccountRequest() {
    	
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
	public int getAccountType() {
		return accountType;
	}
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "AccountRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance='" + balance + '\'' +
                ", cbu='" + cbu + '\'' +
                ", accountType=" + accountType +
                ", active='" + active + '\'' +
                '}';
	}
    
}
