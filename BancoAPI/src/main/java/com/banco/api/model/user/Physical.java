package com.banco.api.model.user;

import com.banco.api.adapter.DateUtils;
import com.banco.api.adapter.Externalizable;
import com.banco.api.dto.user.UserType;
import com.banco.api.dto.user.PhysicalUserDTO;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Physical extends User implements Externalizable<PhysicalUserDTO> {

    private String dni;

    @OneToOne
    @JoinColumn(name = "idAccount")
    @Column(name = "idSavingsAccount")
    private Savings savings; //Caja de Ahorro

    @OneToOne
    @JoinColumn(name = "idAccount")
    @Column(name = "idCheckingAccount")
    private Checking checking; //Cuenta Corriente

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    private String firstName;
    private String lastName;
    private String mobilePhone;

    public Physical() {
        this.userType = UserType.PHYSICAL.getValue();
    }

    public Physical(int id, String cuitCuilCdi, String usr, String address, String phone, String mobilePhone,
                    boolean active, String dni, Savings savings, Checking checking, Date birthDate, String firstName,
                    String lastName) {
        super(id, cuitCuilCdi, usr, address, phone, active);
        this.dni = dni;
        this.savings = savings;
        this.checking = checking;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobilePhone = mobilePhone;
        this.userType = UserType.PHYSICAL.getValue();
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Override
    public PhysicalUserDTO toView() {
        PhysicalUserDTO view = new PhysicalUserDTO();
        view.setActive(this.isActive());
        view.setAddress(this.getAddress());
        view.setCuitCuilCdi(this.getCuitCuilCdi());
        view.setPhone(this.getPhone());
        view.setUsername(this.getUsername());
        view.setUserType(UserType.valueOf(this.getUserType()).toString());
        view.setDni(this.getDni());
        view.setSavings(this.getSavings().toView());
        view.setChecking(this.getChecking().toView());
        view.setBirthDate(DateUtils.format(this.getBirthDate()));
        view.setFirstName(this.getFirstName());
        view.setLastName(this.getLastName());
        view.setMobilePhone(this.getMobilePhone());
        return view;
    }
}
