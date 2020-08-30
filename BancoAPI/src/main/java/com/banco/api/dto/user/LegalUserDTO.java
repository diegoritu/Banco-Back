package com.banco.api.dto.user;

import com.banco.api.dto.account.CheckingDTO;
import com.banco.api.dto.account.SavingsDTO;

public class LegalUserDTO extends UserDTO {

    private String businessName;
    private SavingsDTO savings; //Caja de Ahorro
    private CheckingDTO checking; //Cuenta Corriente

    public LegalUserDTO() {
    }

    public LegalUserDTO(int id, String userType, String cuitCuilCdi, String username, String address, String phone, boolean active,
                        String businessName, SavingsDTO savings, CheckingDTO checking) {
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

    public SavingsDTO getSavings() {
        return savings;
    }

    public void setSavings(SavingsDTO savings) {
        this.savings = savings;
    }

    public CheckingDTO getChecking() {
        return checking;
    }

    public void setChecking(CheckingDTO checking) {
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
