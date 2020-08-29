package com.banco.api.model;


import javax.persistence.Entity;

@Entity
public class Checking extends Account {
    private float maxOverdraft; //Monto máximo para girar en descubierto

    public Checking(String accountNumber, float balance, String alias, String cbu, int accountType,
			float maxOverdraft) {
		super(accountNumber, balance, alias, cbu, accountType);
		this.maxOverdraft = maxOverdraft;
	}

	public float getMaxOverdraft() {
        return maxOverdraft;
    }

    public void setMaxOverdraft(float maxOverdraft) {
        this.maxOverdraft = maxOverdraft;
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
