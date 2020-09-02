package com.banco.api.model.account;


import com.banco.api.adapter.Externalizable;
import com.banco.api.dto.account.AccountType;
import com.banco.api.dto.account.CheckingDTO;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Checking")
public class Checking extends Account implements Externalizable<CheckingDTO> {

    private float maxOverdraft; //Monto máximo para girar en descubierto

    public Checking(float balance, String alias, int accountType, float maxOverdraft) {
		super(balance, alias, accountType);
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

    @Override
    public CheckingDTO toView() {
        CheckingDTO view = new CheckingDTO();
        view.setMaxOverdraft(this.getMaxOverdraft());
        view.setId(this.getIdAccount());
        view.setAccountNumber(this.getAccountNumber());
        view.setAccountType(AccountType.valueOf(this.getAccountType()).toString());
        view.setAlias(this.getAlias());
        view.setBalance(this.getBalance());
        view.setCbu(this.getCbu());
        return view;
    }
}
