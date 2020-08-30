package com.banco.api.model.user;

import com.banco.api.adapter.DateUtils;
import com.banco.api.adapter.Externalizable;
import com.banco.api.dto.user.AdministrativeUserDTO;
import com.banco.api.dto.user.UserType;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Administrative extends User implements Externalizable<AdministrativeUserDTO> {

    private String dni;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthdate;

    private String name;
    private String mobilePhone;

    public Administrative() {
    }

    public Administrative(int id, String cuitCuilCdi, String username, String address, String phone, boolean active,
                          String dni, Date birthdate, String name, String mobilePhone) {
        super(id, cuitCuilCdi, username, address, phone, active);
        this.dni = dni;
        this.birthdate = birthdate;
        this.name = name;
        this.mobilePhone = mobilePhone;
        this.userType = UserType.ADMINISTRATIVE.getValue();
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
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
        return "Administrative{" +
                "dni='" + dni + '\'' +
                ", birthdate=" + birthdate +
                ", name='" + name + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                '}';
    }

    @Override
    public AdministrativeUserDTO toView() {
        AdministrativeUserDTO view = new AdministrativeUserDTO();
        view.setId(this.getId());
        view.setActive(this.isActive());
        view.setAddress(this.getAddress());
        view.setCuitCuilCdi(this.getCuitCuilCdi());
        view.setPhone(this.getPhone());
        view.setUsername(this.getUsername());
        view.setUserType(UserType.valueOf(this.getUserType()).toString());
        view.setBirthDate(DateUtils.format(this.getBirthdate()));
        view.setDni(this.getDni());
        view.setMobilePhone(this.getMobilePhone());
        view.setName(this.getName());
        return view;
    }
}
