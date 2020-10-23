package com.banco.api.dto.movement.request;

import java.util.List;

import com.banco.api.dto.others.request.CreditEntityDepositCommerceTransaction;

public class CreditEntityDepositCommerceRequest {
	private String creditEntityCBU;
	private List<CreditEntityDepositCommerceTransaction> transactions;
	
	public CreditEntityDepositCommerceRequest() {}

	public CreditEntityDepositCommerceRequest(String creditEntityCBU,
			List<CreditEntityDepositCommerceTransaction> transactions) {
		super();
		this.creditEntityCBU = creditEntityCBU;
		this.transactions = transactions;
	}

	public String getCreditEntityCBU() {
		return creditEntityCBU;
	}

	public void setCreditEntityCBU(String creditEntityCBU) {
		this.creditEntityCBU = creditEntityCBU;
	}

	public List<CreditEntityDepositCommerceTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<CreditEntityDepositCommerceTransaction> transactions) {
		this.transactions = transactions;
	}
}
