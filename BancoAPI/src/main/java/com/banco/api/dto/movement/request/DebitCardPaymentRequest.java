package com.banco.api.dto.movement.request;

public class DebitCardPaymentRequest {
	private String businessCbu;
	private String number;
	private String securityCode;
	private String expirationDate;
	private String amount;
	private String concept;
	
	public DebitCardPaymentRequest() {}
	
	public DebitCardPaymentRequest(String businessCbu, String number, String securityCode, String expirationDate,
			String amount, String concept) {
		super();
		this.businessCbu = businessCbu;
		this.number = number;
		this.securityCode = securityCode;
		this.expirationDate = expirationDate;
		this.amount = amount;
		this.concept = concept;
	}
	
	public String getBusinessCbu() {
		return businessCbu;
	}
	public String getNumber() {
		return number;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public String getAmount() {
		return amount;
	}
	public String getConcept() {
		return concept;
	}

	@Override
	public String toString() {
		return "DebitCardPaymentRequest [businessCbu=" + businessCbu + ", number=" + number + ", securityCode="
				+ securityCode + ", expirationDate=" + expirationDate + ", amount=" + amount + ", concept=" + concept
				+ "]";
	}
}
