package com.banco.api.model;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Administrative extends User {
	private String dni;
	private Date birthdate;
	private String name;
	private int idAdministrative;
	
	public Administrative(int id, String cuitCuilCdi, String usr, String address, String phone, String mobilePhone,
			boolean active, String dni, Date birthdate, String name, int idAdministrative) {
		super(id, cuitCuilCdi, usr, address, phone, mobilePhone, active);
		this.dni = dni;
		this.birthdate = birthdate;
		this.name = name;
		this.idAdministrative = idAdministrative;
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

	public int getIdAdministrative() {
		return idAdministrative;
	}

	public void setIdAdministrative(int idAdministrative) {
		this.idAdministrative = idAdministrative;
	}
}
