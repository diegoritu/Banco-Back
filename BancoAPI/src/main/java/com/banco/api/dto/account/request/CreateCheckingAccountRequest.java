package com.banco.api.dto.account.request;

public class CreateCheckingAccountRequest {

    private String username;
    private Float maxOverdraft;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Float getMaxOverdraft() {
        return maxOverdraft;
    }

    public void setMaxOverdraft(Float maxOverdraft) {
        this.maxOverdraft = maxOverdraft;
    }
}
