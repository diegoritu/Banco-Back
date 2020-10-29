package com.banco.api.dto.movement.request;

public class TransferBetweenOwnAccountsRequest {
	private float amount;
	private String accountNumberFrom;
	private String accountNumberTo;
	
	public TransferBetweenOwnAccountsRequest(float amount, String accountNumberFrom, String accountNumberTo) {
		super();
		this.amount = amount;
		this.accountNumberFrom = accountNumberFrom;
		this.accountNumberTo = accountNumberTo;
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
	public String getAccountNumberTo() {
		return accountNumberTo;
	}
	public void setAccountNumberTo(String accountNumberTo) {
		this.accountNumberTo = accountNumberTo;
	}
	
	
	@Override
	public String toString() {
		return "TransferenceBetweenOwnAccountsRequest [amount=" + amount + ", accountNumberFrom=" + accountNumberFrom
				+ ", accountNumberTo=" + accountNumberTo + "]";
	}
		
}
