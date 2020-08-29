package com.banco.api.model;


import javax.persistence.Entity;

@Entity
public class Savings extends Account {
    private float interestRate;

    public Savings(String accountNumber, float balance, String alias, String cbu, int accountType,
			float interestRate) {
		super(accountNumber, balance, alias, cbu, accountType);
		this.interestRate = interestRate;
	}

	public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public void deposit() {
        // TODO Auto-generated method stub

    }

    @Override
    public void extract() {
        // TODO Auto-generated method stub

    }
}
