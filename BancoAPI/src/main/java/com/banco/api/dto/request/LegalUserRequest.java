package com.banco.api.dto.request;

public class LegalUserRequest extends UserRequest {

    private String businessName;

    public LegalUserRequest() {
    }

    public String getBusinessName() {
        return businessName;
    }
}
