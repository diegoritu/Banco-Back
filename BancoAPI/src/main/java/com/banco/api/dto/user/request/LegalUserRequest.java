package com.banco.api.dto.user.request;

public class LegalUserRequest extends UserRequest {

    private String businessName;
    private boolean withCheckingAccount;
    private Float maxOverdraft;

    public LegalUserRequest() {
    }

    public String getBusinessName() {
        return businessName;
    }

	public boolean isWithCheckingAccount() {
		return withCheckingAccount;
	}

    public Float getMaxOverdraft() {
        return maxOverdraft;
    }
}
