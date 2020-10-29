package com.banco.api.dto.account.request;

public class UpdateCheckingAccountRequest {

    private String accountNumber;
    private Float maxOverDraft;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Float getMaxOverDraft() {
        return maxOverDraft;
    }

    public void setMaxOverDraft(Float maxOverDraft) {
        this.maxOverDraft = maxOverDraft;
    }
}
