package com.banco.api.dto.others;

public class APIErrorMessageDTO {
	private int status;
	private String errorCode;
	private String error;
	
	public APIErrorMessageDTO(int status, String errorCode, String error) {
		super();
		this.status = status;
		this.errorCode = errorCode;
		this.error = error;
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
