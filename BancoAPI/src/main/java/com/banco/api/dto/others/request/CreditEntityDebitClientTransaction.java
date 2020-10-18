package com.banco.api.dto.others.request;

public class CreditEntityDebitClientTransaction {
	private String debtorCBU;
	private float debtAmount;
	
	public CreditEntityDebitClientTransaction() {}
	
	public CreditEntityDebitClientTransaction(String debtorCBU, float debtAmount) {
		super();
		this.debtorCBU = debtorCBU;
		this.debtAmount = debtAmount;
	}

	public String getDebtorCBU() {
		return debtorCBU;
	}
	public void setDebtorCBU(String debtorCBU) {
		this.debtorCBU = debtorCBU;
	}
	public float getDebtAmount() {
		return debtAmount;
	}
	public void setDebtAmount(float debtAmount) {
		this.debtAmount = debtAmount;
	}
	
	
	
}
