package com.banco.api.dto.account;

public class CheckingDTO extends AccountDTO {

    private float maxOverdraft;

    public CheckingDTO() {
        super();
    }

    public CheckingDTO(int id, String accountNumber, float balance, boolean active, String cbu, String accountType,
                       float maxOverdraft) {
        super(id, accountNumber, balance, active, cbu, accountType);
        this.maxOverdraft = maxOverdraft;
    }

    public float getMaxOverdraft() {
        return maxOverdraft;
    }

    public void setMaxOverdraft(float maxOverdraft) {
        this.maxOverdraft = maxOverdraft;
    }

    @Override
    public String toString() {
        return "CheckingDTO{" +
                "maxOverdraft=" + maxOverdraft +
                '}';
    }
}
