package com.banco.api.dto.user.request;

public class LegalUserRequest extends UserRequest {

    private String businessName;
    private boolean withCheckingAccount;

    public LegalUserRequest() {
    }

    public String getBusinessName() {
        return businessName;
    }

	public boolean isWithCheckingAccount() {
		return withCheckingAccount;
	}
    
    
}
