package com.banco.api.dto.others;

import com.banco.api.dto.account.SavingsDTO;

public class DebitCardDTO {
	
	private String number;
	private String securityCode;
	private String expirationDate;
	private SavingsDTO savingsAccount;
	
	public DebitCardDTO() {}
	
	public DebitCardDTO(String number, String securityCode, String expirationDate, SavingsDTO savingsAccount) {
		super();
		this.number = number;
		this.securityCode = securityCode;
		this.expirationDate = expirationDate;
		this.savingsAccount = savingsAccount;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	public SavingsDTO getSavingsAccount() {
		return savingsAccount;
	}

	public void setSavingsAccount(SavingsDTO savingsAccount) {
		this.savingsAccount = savingsAccount;
	}

	@Override
	public String toString() {
		return "DebitCardDTO [number=" + number + ", securityCode=" + securityCode + ", expirationDate="
				+ expirationDate + ", savingsAccount=" + savingsAccount + "]";
	}
}
