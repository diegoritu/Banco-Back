package com.banco.api.dto.others;

public class TransactionIdDTO {
	private long transactionId;

	public TransactionIdDTO(long transactionId) {
		super();
		this.transactionId = transactionId;
	}

	public TransactionIdDTO() {
		// TODO Auto-generated constructor stub
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	
	
}
