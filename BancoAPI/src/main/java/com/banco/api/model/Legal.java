package com.banco.api.model;

import org.springframework.stereotype.Component;

@Component
public class Legal extends User {
	private String businessName;
	private Account savings; //Caja de Ahorro
	private Account checking; //Cuenta Corriente
	private int idLegal;
	
	public Legal(int id, String cuitCuilCdi, String usr, String address, String phone, String mobilePhone,
			boolean active, String businessName, Account savings, Account checking) {
		super(id, cuitCuilCdi, usr, address, phone, mobilePhone, active);
		this.businessName = businessName;
		this.savings = savings;
		this.checking = checking;
	}

	public String getBusinessName() {
		return businessName;
	}

	public int getIdLegal() {
		return idLegal;
	}

	public void setIdLegal(int idLegal) {
		this.idLegal = idLegal;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
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
	
}
