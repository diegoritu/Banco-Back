package com.banco.api.model.user;

import com.banco.api.adapter.DateUtils;
import com.banco.api.adapter.Externalizable;
import com.banco.api.adapter.Internalizable;
import com.banco.api.dto.user.PhysicalUserDTO;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.dto.user.UserType;

import javax.persistence.*;
import java.sql.Date;


@Entity
public class Physical
        extends User
        implements Internalizable<PhysicalUserDTO>, Externalizable<PhysicalUserDTO> {

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
    private Date birthday;

    private String name;
    private String mobilePhone;


    public Physical(int id, String cuitCuilCdi, String usr, String address, String phone, String mobilePhone,
                    boolean active, String dni, Savings savings, Checking checking, Date birthday, String name) {
        super(id, cuitCuilCdi, usr, address, phone, active);
        this.dni = dni;
        this.savings = savings;
        this.checking = checking;
        this.birthday = birthday;
        this.name = name;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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
        return "Physical{" +
                "dni='" + dni + '\'' +
                ", savings=" + savings +
                ", checking=" + checking +
                ", birthday=" + birthday +
                ", name='" + name + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                '}';
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
        view.setBirthDate(DateUtils.format(this.getBirthday()));
        view.setName(this.getName());
        view.setMobilePhone(this.getMobilePhone());
        return view;
    }

    @Override
    public void fromView(PhysicalUserDTO view) {
        this.setAddress(view.getAddress());
        this.setCuitCuilCdi(view.getCuitCuilCdi());
        this.setPhone(view.getPhone());
        this.setUsername(view.getUsername());
        this.setDni(view.getDni());
        this.setBirthday(DateUtils.parse(view.getBirthDate())); //TODO definir tipo de fecha
        this.setName(view.getName());
        this.setMobilePhone(view.getMobilePhone());
    }
}
