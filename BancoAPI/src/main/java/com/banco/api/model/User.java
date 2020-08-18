package com.banco.api.model;

import org.springframework.stereotype.Component;

@Component
public class User {
	private int idUsr;
	private String cuitCuilCdi;
	private String usr;
	private String address;
	private String phone;
	private String mobilePhone;
	private boolean active;
	
	public User(int id, String cuitCuilCdi, String usr, String address, String phone, String mobilePhone,
			boolean active) {
		super();
		this.idUsr = id;
		this.cuitCuilCdi = cuitCuilCdi;
		this.usr = usr;
		this.address = address;
		this.phone = phone;
		this.mobilePhone = mobilePhone;
		this.active = active;
	}

	public int getId() {
		return idUsr;
	}

	public void setId(int id) {
		this.idUsr = id;
	}

	public String getCUITCUILCDI() {
		return cuitCuilCdi;
	}

	public void setCUITCUILCDI(String cUITCUILCDI) {
		cuitCuilCdi = cUITCUILCDI;
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

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
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
