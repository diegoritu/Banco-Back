package com.banco.api.model.user;

import com.banco.api.model.account.Account;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Legal extends User {
    private String businessName;
    
    @OneToOne
    @JoinColumn(name = "idAccount")
    @Column(name = "idSavingsAccount")
    private Savings savings; //Caja de Ahorro
    
    @OneToOne
    @JoinColumn(name = "idAccount")
    @Column(name = "idCheckingAccount")
    private Checking checking; //Cuenta Corriente

    public Legal(int id, String cuitCuilCdi, String usr, String address, String phone,
                 boolean active, String businessName, Savings savings, Checking checking) {
        super(id, cuitCuilCdi, usr, address, phone, active);
        this.businessName = businessName;
        this.savings = savings;
        this.checking = checking;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Account getSavings() {
        return savings;
    }

    public void setSavings(Savings savings) {
        this.savings = savings;
    }

    public Account getChecking() {
        return checking;
    }

    public void setChecking(Checking checking) {
        this.checking = checking;
    }

    @Override
    public String toString() {
        return "Legal{" +
                "businessName='" + businessName + '\'' +
                ", savings=" + savings +
                ", checking=" + checking +
                '}';
    }
}
