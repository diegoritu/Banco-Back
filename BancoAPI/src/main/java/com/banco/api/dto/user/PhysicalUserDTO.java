package com.banco.api.dto.user;

import com.banco.api.dto.account.CheckingDTO;
import com.banco.api.dto.account.SavingsDTO;

public class PhysicalUserDTO extends UserDTO {

    private String dni;
    private SavingsDTO savings; //Caja de Ahorro
    private CheckingDTO checking; //Cuenta Corriente
    private String birthDate;
    private String name;
    private String mobilePhone;

    public PhysicalUserDTO() {
    }

    public PhysicalUserDTO(int userId, String userType, String cuitCuilCdi, String username, String address,
                           String phone, boolean active,
                           String dni, SavingsDTO savings, CheckingDTO checking, String birthDate, String name, String mobilePhone) {
        super(userId, userType, cuitCuilCdi, username, address, phone, active);
        this.dni = dni;
        this.savings = savings;
        this.checking = checking;
        this.birthDate = birthDate;
        this.name = name;
        this.mobilePhone = mobilePhone;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Override
    public String toString() {
        return "PhysicalUserDTO{" +
                "dni='" + dni + '\'' +
                ", savings=" + savings +
                ", checking=" + checking +
                ", birthDate='" + birthDate + '\'' +
                ", name='" + name + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                '}';
    }
}
