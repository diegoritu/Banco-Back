package com.banco.api.dto.others;

import java.util.ArrayList;
import java.util.List;

public class CreditEntityDebitClientsResponseWithFailuresDTO {
	private List<Integer> transactionIds;
	private List<CreditEntityDebitClientsFailures> failures;
	
	public CreditEntityDebitClientsResponseWithFailuresDTO(List<Integer> transactionIds,
			List<CreditEntityDebitClientsFailures> failures) {
		super();
		this.transactionIds = transactionIds;
		this.failures = failures;
	}

	public List<CreditEntityDebitClientsFailures> getFailures() {
		return failures;
	}

	public void setFailures(List<CreditEntityDebitClientsFailures> failures) {
		this.failures = failures;
	}

	public List<Integer> getTransactionIds() {
		return transactionIds;
	}

	public void setTransactionIds(List<Integer> transactionIds) {
		this.transactionIds = transactionIds;
	}
	
	

}
