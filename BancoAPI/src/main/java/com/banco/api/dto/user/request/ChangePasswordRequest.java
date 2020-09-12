package com.banco.api.dto.user.request;

public class ChangePasswordRequest {
	private String username;
	private String password;
	
	public ChangePasswordRequest() {}
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "ChangePasswordRequest [username=" + username + ", password=" + password + "]";
	}
}
