package com.banco.api.model;

import java.util.TreeMap;

public class Checking extends Account {
    private float maxOverdraft; //Monto máximo para girar en descubierto

    public Checking(String accountNumber, float balance, String alias, TreeMap<Movement, Float> movements, String cbu,
                    float maxOverdraft) {
        super(accountNumber, balance, alias, movements, cbu);
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
