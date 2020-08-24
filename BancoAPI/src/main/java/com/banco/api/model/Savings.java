package com.banco.api.model;

import java.util.TreeMap;

public class Savings extends Account {
	private float interestRate;

	public Savings(String accountNumber, float balance, String alias, TreeMap<Movement, Float> movements, String cbu,
			float interestRate) {
		super(accountNumber, balance, alias, movements, cbu);
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
