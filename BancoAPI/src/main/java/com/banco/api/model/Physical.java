package com.banco.api.model;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class Physical extends User {
	private String dni;
	private Account savings; //Caja de Ahorro
	private Account checking; //Cuenta Corriente
	private Date birthday;
	private String name;
	private int idPhysical;
	
	public Physical(int id, String cuitCuilCdi, String usr, String address, String phone, String mobilePhone,
			boolean active, String dni, Account savings, Account checking, Date birthday, String name, int idPhysical) {
		super(id, cuitCuilCdi, usr, address, phone, mobilePhone, active);
		this.dni = dni;
		this.savings = savings;
		this.checking = checking;
		this.birthday = birthday;
		this.name = name;
		this.idPhysical = idPhysical;
	}
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public Account getSavings() {
		return savings;
	}
	public void setSavings(Account savings) {
		this.savings = savings;
	}
	public Account getChecking() {
		return checking;
	}
	public void setChecking(Account checking) {
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
	public int getIdPhysical() {
		return idPhysical;
	}
	public void setIdPhysical(int idPhysical) {
		this.idPhysical = idPhysical;
	}
}
