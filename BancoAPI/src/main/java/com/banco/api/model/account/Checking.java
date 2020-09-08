package com.banco.api.model.account;


import com.banco.api.adapter.Externalizable;
import com.banco.api.dto.account.AccountType;
import com.banco.api.dto.account.CheckingDTO;
import com.banco.api.dto.user.UserType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Checking")
public class Checking extends Account implements Externalizable<CheckingDTO> {

    private float maxOverdraft; //Monto máximo para girar en descubierto

    public Checking(float balance, int accountType, float maxOverdraft) {
		super(balance, accountType);
		this.maxOverdraft = maxOverdraft;
	}
    
    public Checking() 
    {
    	super();
    }

    public Checking(float maxOverdraft, float balance, int accountType, boolean active, String cbu, String accountNumber) {
    	super(balance, accountType, active, cbu, accountNumber);
    	this.maxOverdraft = maxOverdraft;
    }
    
	public float getMaxOverdraft() {
        return maxOverdraft;
    }

    public void setMaxOverdraft(float maxOverdraft) {
        this.maxOverdraft = maxOverdraft;
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
        	if(Math.abs(dif) <= maxOverdraft) {
        		setBalance(balance - amount);
        		return true;
        	}
        	else {
        		return false;
        	}
        }
        else {
        	setBalance(balance-amount);
        	return true;
        }
    }

    @Override
    public CheckingDTO toView() {
        CheckingDTO view = new CheckingDTO();
        view.setMaxOverdraft(this.getMaxOverdraft());
        view.setId(this.getIdAccount());
        view.setAccountNumber(this.getAccountNumber());
        view.setAccountType(AccountType.valueOf(this.getAccountType()).toString());
        view.setActive(this.isActive());
        view.setBalance(this.getBalance());
        view.setCbu(this.getCbu());
        return view;
    }
}
