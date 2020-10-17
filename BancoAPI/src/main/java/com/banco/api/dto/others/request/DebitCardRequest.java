package com.banco.api.dto.others.request;


public class DebitCardRequest {
	
	private String number;
	private String securityCode;
	private String expirationDate;
	
	public DebitCardRequest() {}
	
	public DebitCardRequest(String number, String securityCode, String expirationDate) {
		super();
		this.number = number;
		this.securityCode = securityCode;
		this.expirationDate = expirationDate;
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

	@Override
	public String toString() {
		return "DebitCardDTO [number=" + number + ", securityCode=" + securityCode + ", expirationDate="
				+ expirationDate + "]";
	}
}