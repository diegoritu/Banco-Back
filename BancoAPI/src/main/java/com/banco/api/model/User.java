package com.banco.api.model;

import org.springframework.stereotype.Component;

public class User {
	private int idUsr;
	private UserType userType;
	private String cuitCuilCdi;
	private String usr;
	private String address;
	private String phone;
	private boolean active;
	
	public User(int id, String cuitCuilCdi, String usr, String address, String phone,
			boolean active) {
		super();
		this.idUsr = id;
		this.cuitCuilCdi = cuitCuilCdi;
		this.usr = usr;
		this.address = address;
		this.phone = phone;
		this.active = active;
	}

	public int getId() {
		return idUsr;
	}

	public void setId(int id) {
		this.idUsr = id;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getCuitCuilCdi() {
		return cuitCuilCdi;
	}

	public void setCuitCuilCdi(String cuitCuilCdi) {
		this.cuitCuilCdi = cuitCuilCdi;
	}

	public String getUsr() {
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void ay() {
		System.out.println("EYYY");
	}
}
