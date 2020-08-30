package com.banco.api.model.internal.account;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Account {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int idAccount;
	
    private String accountNumber;
    private float balance; //Saldo
    private String alias;
    private String cbu;
    
    /*	Database meanings for accountType:
     * CHECKINGACCOUNT = 0
     * SAVINGSACCOUNT = 1
     */
    private int accountType;

    public Account(String accountNumber, float balance, String alias, String cbu,
			int accountType) {
		super();
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.alias = alias;
		this.cbu = cbu;
		this.accountType = accountType;
	}

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
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

    public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public abstract void deposit();

    public abstract void extract();

}
