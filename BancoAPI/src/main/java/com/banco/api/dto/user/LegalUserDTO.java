package com.banco.api.dto.user;

import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;

public class LegalUserDTO extends UserDTO {

    private String businessName;
    private Savings savings; //Caja de Ahorro
    private Checking checking; //Cuenta Corriente

    public LegalUserDTO() {
    }

    public LegalUserDTO(int id, String userType, String cuitCuilCdi, String username, String address, String phone, boolean active,
                        String businessName, Savings savings, Checking checking) {
        super(id, userType, cuitCuilCdi, username, address, phone, active);
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

    public Savings getSavings() {
        return savings;
    }

    public void setSavings(Savings savings) {
        this.savings = savings;
    }

    public Checking getChecking() {
        return checking;
    }

    public void setChecking(Checking checking) {
        this.checking = checking;
    }

    @Override
    public String toString() {
        return "LegalUserDTO{" +
                "businessName='" + businessName + '\'' +
                ", savings=" + savings +
                ", checking=" + checking +
                '}';
    }
}
