package com.banco.api.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="checking_accounts")
public class CheckingEntity {
	@Id	
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int idCheckingAccount;
	private String accountNumber;
	private float balance;
	private String alias;
	private String cbu;
	private float maxOverdraft;
	
	public CheckingEntity(int idCheckingAccount, String accountNumber, float balance, String alias, String cbu,
			float maxOverdraft) {
		super();
		this.idCheckingAccount = idCheckingAccount;
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.alias = alias;
		this.cbu = cbu;
		this.maxOverdraft = maxOverdraft;
	}
	
	public int getIdCheckingAccount() {
		return idCheckingAccount;
	}
	public void setIdCheckingAccount(int idCheckingAccount) {
		this.idCheckingAccount = idCheckingAccount;
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
	public float getMaxOverdraft() {
		return maxOverdraft;
	}
	public void setMaxOverdraft(float maxOverdraft) {
		this.maxOverdraft = maxOverdraft;
	}
}
