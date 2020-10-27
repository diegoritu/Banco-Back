package com.banco.api.published.response;

public class PublishedErrorMessageDTO {

	private int status;
	private String errorCode;
	private String error;

	public PublishedErrorMessageDTO(int status, String errorCode, String error) {
		super();
		this.status = status;
		this.errorCode = errorCode;
		this.error = error;
	}

	public int getStatus() {
		return status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getError() {
		return error;
	}
}
