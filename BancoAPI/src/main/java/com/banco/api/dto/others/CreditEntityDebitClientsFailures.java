package com.banco.api.dto.others;

public class CreditEntityDebitClientsFailures {
	private String debtorCBU;
	private int status;
	private String errorCode;
	private String error;
	
	public CreditEntityDebitClientsFailures(String debtorCBU, int status, String errorCode, String error) {
		super();
		this.debtorCBU = debtorCBU;
		this.status = status;
		this.errorCode = errorCode;
		this.error = error;
	}

	public String getDebtorCBU() {
		return debtorCBU;
	}

	public void setDebtorCBU(String debtorCBU) {
		this.debtorCBU = debtorCBU;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
}
