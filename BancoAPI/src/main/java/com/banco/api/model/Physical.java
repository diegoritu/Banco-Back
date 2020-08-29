package com.banco.api.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Physical extends User {
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
                    boolean active, String dni, Savings savings, Checking checking, Date birthday, String name, int idPhysical) {
        super(id, cuitCuilCdi, usr, address, phone, active);
        this.dni = dni;
        this.savings = savings;
        this.checking = checking;
        this.birthday = birthday;
        this.name = name;
        this.mobilePhone = mobilePhone;
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
}
