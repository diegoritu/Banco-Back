package com.banco.api.model.account;


import com.banco.api.adapter.Externalizable;
import com.banco.api.dto.account.AccountType;
import com.banco.api.dto.account.SavingsDTO;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Savings")
public class Savings extends Account implements Externalizable<SavingsDTO> {

    private float interestRate;

    public Savings() {
		super(AccountType.SAVINGS.getValue());
	}

	public Savings(float interestRate) {
    	super(AccountType.SAVINGS.getValue());
        this.interestRate = interestRate;
    }

	public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public void deposit(float amount) {
        setBalance(balance + amount);
    }

    @Override
    //Returns true or false depending on whether the extraction can be performed or not.
    public boolean extract(float amount) {
    	float dif = balance - amount;
    	if(dif < 0) {
    		return false;
    	}
    	else {
    		setBalance(balance - amount);
    		return true;
    	}
    }

    @Override
    public String toString() {
        return "Savings{" +
                "interestRate=" + interestRate +
                '}';
    }

    @Override
    public SavingsDTO toView() {
        SavingsDTO view = new SavingsDTO();
        view.setInterestRate(this.getInterestRate());
        view.setId(this.getIdAccount());
        view.setAccountNumber(this.getAccountNumber());
        view.setAccountType(AccountType.valueOf(this.getAccountType()).toString());
        view.setActive(this.isActive());
        view.setBalance(this.getBalance());
        view.setCbu(this.getCbu());
        return view;
    }
}
