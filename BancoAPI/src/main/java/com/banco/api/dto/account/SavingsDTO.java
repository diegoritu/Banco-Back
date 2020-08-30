package com.banco.api.dto.account;

public class SavingsDTO extends AccountDTO {

    private float interestRate;

    public SavingsDTO() {
        super();
    }

    public SavingsDTO(int id, String accountNumber, float balance, String alias, String cbu, String accountType,
                      float interestRate) {
        super(id, accountNumber, balance, alias, cbu, accountType);
        this.interestRate = interestRate;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "SavingsDTO{" +
                "interestRate=" + interestRate +
                '}';
    }
}
