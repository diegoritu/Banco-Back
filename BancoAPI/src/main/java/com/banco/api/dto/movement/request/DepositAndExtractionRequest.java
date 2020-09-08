package com.banco.api.dto.movement.request;

import java.io.Serializable;

public class DepositAndExtractionRequest implements Serializable{
	private float amount;
    private String accountNumberEntryAccount;
    
    
    public DepositAndExtractionRequest() {}


	public DepositAndExtractionRequest(int movementType, String dayAndHour, String concept, float amount,
			String reference, String accountNumberEntryAccount, String accountNumberExitAccount) {
		super();
		this.amount = amount;
		this.accountNumberEntryAccount = accountNumberEntryAccount;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getAccountNumberEntryAccount() {
		return accountNumberEntryAccount;
	}

	public void setAccountNumberEntryAccount(String accountNumberEntryAccount) {
		this.accountNumberEntryAccount = accountNumberEntryAccount;
	}

	@Override
	public String toString() {
		return "DepositRequest [amount=" + amount + ", accountNumberEntryAccount=" + accountNumberEntryAccount + "]";
	}
}
