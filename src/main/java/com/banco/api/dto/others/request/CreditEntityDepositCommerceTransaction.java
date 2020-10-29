package com.banco.api.dto.others.request;

public class CreditEntityDepositCommerceTransaction {
	
	private String businessCBU;
	private float amount;
	
	public CreditEntityDepositCommerceTransaction() {}
	
	public CreditEntityDepositCommerceTransaction(String businessCBU, float amount) {
		super();
		this.businessCBU = businessCBU;
		this.amount = amount;
	}

	public String getBusinessCBU() {
		return businessCBU;
	}

	public void setBusinessCBU(String businessCBU) {
		this.businessCBU = businessCBU;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	
}
