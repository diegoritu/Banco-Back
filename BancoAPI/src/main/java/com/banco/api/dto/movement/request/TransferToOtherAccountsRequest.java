package com.banco.api.dto.movement.request;

public class TransferToOtherAccountsRequest {
	private float amount;
	private String accountNumberFrom;
	private String cbuTo;
	private String reference;
	
	public TransferToOtherAccountsRequest(float amount, String accountNumberFrom, String cbuTo, String reference) {
		super();
		this.amount = amount;
		this.accountNumberFrom = accountNumberFrom;
		this.cbuTo = cbuTo;
		this.reference = reference;
	}
	
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getAccountNumberFrom() {
		return accountNumberFrom;
	}
	public void setAccountNumberFrom(String accountNumberFrom) {
		this.accountNumberFrom = accountNumberFrom;
	}
	public String getCbuTo() {
		return cbuTo;
	}
	public void setCbuTo(String cbuTo) {
		this.cbuTo = cbuTo;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	@Override
	public String toString() {
		return "TransferToOtherAccountsRequest [amount=" + amount + ", accountNumberFrom=" + accountNumberFrom
				+ ", cbuTo=" + cbuTo + ", reference=" + reference + "]";
	}
}
