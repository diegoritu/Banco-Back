package com.banco.api.model.account;


import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.banco.api.service.account.AccountService;

@Entity
@Table(name = "accounts")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Account", discriminatorType = DiscriminatorType.STRING)
public abstract class Account {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idAccount;
	
    private String accountNumber;
    private float balance; //Saldo
    private boolean active;
    private String cbu;
    
    /*	Database meanings for accountType:
     * CHECKINGACCOUNT = 0
     * SAVINGSACCOUNT = 1
     */
    private int accountType;

    public Account(float balance, int accountType) {
		super();
		this.accountNumber = generateAccountNumber();
		this.balance = balance;
		this.active = true;
		this.cbu = generateCbu();
		this.accountType = accountType;
	}

    private String generateCbu() {
		String first = Integer.toString(Math.abs(ThreadLocalRandom.current().nextInt(((99999999 - 10000000) + 1) + 10000000)));
		String last = Integer.toString(Math.abs(new Random().nextInt(((10-1)+1)+1)));
		String cbu = first + accountNumber + last;
		return cbu;
	}

	private String generateAccountNumber() {
		String accountNumber = Long.toString(Math.abs(ThreadLocalRandom.current().nextLong(((9999999999999L - 1000000000000L) + 1) + 1000000000000L)));
		return accountNumber;
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

	public abstract void deposit();

    public abstract void extract();

}
