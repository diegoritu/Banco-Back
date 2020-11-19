package com.banco.api.model.account;


import javax.persistence.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "accounts")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="Account", discriminatorType = DiscriminatorType.STRING)
public abstract class Account {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int idAccount;
	
	protected String accountNumber;
	protected float balance; //Saldo
	protected boolean active;
	protected String cbu;
    
    /*	Database meanings for accountType:
     * CHECKINGACCOUNT = 0
     * SAVINGSACCOUNT = 1
     */
	protected int accountType;

    public Account(int accountType) {
		super();
		this.accountNumber = generateAccountNumber();
		this.balance = 0;
		this.active = true;
		this.cbu = generateCbu();
		this.accountType = accountType;
	}
    
    public Account() {
	}

    private String generateCbu() {
    	String cbu;
    	do {
			String first = Integer.toString(Math.abs(ThreadLocalRandom.current().nextInt(((99999999 - 10000000) + 1) + 10000000)));
			String last = Integer.toString(Math.abs(new Random().nextInt(((10-1)+1)+1)));
			cbu = first + accountNumber + last;
    	}
    	while(cbu.length() != 22);
    	return cbu;
	}

	private String generateAccountNumber() {
		String accountNumber;
		do {
			accountNumber = Long.toString(Math.abs(ThreadLocalRandom.current().nextLong(((9999999999999L - 1000000000000L) + 1) + 1000000000000L)));
		}
		while(accountNumber.length() != 13);
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

	public abstract void deposit(float amount);
	
    //Returns true or false depending on whether the extraction can be performed or not.
    public abstract boolean extract(float amount);

}
