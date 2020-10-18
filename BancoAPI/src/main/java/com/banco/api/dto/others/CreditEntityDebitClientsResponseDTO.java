package com.banco.api.dto.others;

import java.util.List;

public class CreditEntityDebitClientsResponseDTO {
	private List<Integer> transactionIds;

	public CreditEntityDebitClientsResponseDTO(List<Integer> transactionIds) {
		super();
		this.transactionIds = transactionIds;
	}

	public List<Integer> getTransactionIds() {
		return transactionIds;
	}

	public void setTransactionIds(List<Integer> transactionIds) {
		this.transactionIds = transactionIds;
	}

	
}
