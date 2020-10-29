package com.banco.api.dto.movement.request;

import java.util.List;

import com.banco.api.dto.others.request.CreditEntityDebitClientTransaction;

public class CreditEntityDebitClientsRequest {

	private String creditEntityCBU;
	private List<CreditEntityDebitClientTransaction> transactions;
	
	public CreditEntityDebitClientsRequest() {}

	public CreditEntityDebitClientsRequest(String creditEntityCBU,
			List<CreditEntityDebitClientTransaction> transactions) {
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

	public List<CreditEntityDebitClientTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<CreditEntityDebitClientTransaction> transactions) {
		this.transactions = transactions;
	}
	
	
}
