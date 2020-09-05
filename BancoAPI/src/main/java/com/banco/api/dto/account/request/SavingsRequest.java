package com.banco.api.dto.account.request;

public class SavingsRequest extends AccountRequest {

	private float interestRate;

    public SavingsRequest() {
    }

	public float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}

	
	
}
