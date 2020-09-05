package com.banco.api.dto.account.request;

public class CheckingRequest extends AccountRequest {
    
	private float maxOverdraft;

    public CheckingRequest() {
    }

	
	public float getMaxOverdraft() {
		return maxOverdraft;
	}

	public void setMaxOverdraft(float maxOverdraft) {
		this.maxOverdraft = maxOverdraft;
	}
	
	

}
