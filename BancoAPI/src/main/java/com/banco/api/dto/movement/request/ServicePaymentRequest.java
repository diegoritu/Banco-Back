package com.banco.api.dto.movement.request;

public class ServicePaymentRequest {
	String idServicePayment;
	String vendorId;
	String accountNumberFrom;
	String usernameFrom;
	
	
	public ServicePaymentRequest(String idServicePayment, String vendorId, String accountNumberFrom, String usernameFrom) {
		super();
		this.idServicePayment = idServicePayment;
		this.vendorId = vendorId;
		this.accountNumberFrom = accountNumberFrom;
		this.usernameFrom = usernameFrom;
	}
	
	public ServicePaymentRequest() {}
	
	public String getIdServicePayment() {
		return idServicePayment;
	}

	public String getVendorId() {
		return vendorId;
	}

	public String getAccountNumberFrom() {
		return accountNumberFrom;
	}

	public String getUsernameFrom() {
		return usernameFrom;
	}

	@Override
	public String toString() {
		return "ServicePaymentRequest [idServicePayment=" + idServicePayment + ", vendorId=" + vendorId
				+ ", accountNumberFrom=" + accountNumberFrom + ", usernameFrom=" + usernameFrom + "]";
	}
}
