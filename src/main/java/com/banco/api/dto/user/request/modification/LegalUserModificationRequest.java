package com.banco.api.dto.user.request.modification;

public class LegalUserModificationRequest extends UserModificationRequest{
	private String businessName;

	public LegalUserModificationRequest() {}
	
	public String getBusinessName() {
		return businessName;
	}

	@Override
	public String toString() {
		return "LegalModificationRequest [businessName=" + businessName + "]";
	}
}
