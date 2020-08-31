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

    public Savings(String accountNumber, float balance, String alias, String cbu,
                   float interestRate) {
        super(accountNumber, balance, alias, cbu, AccountType.SAVINGS.getValue());
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
        view.setAlias(this.getAlias());
        view.setBalance(this.getBalance());
        view.setCbu(this.getCbu());
        return view;
    }
}
