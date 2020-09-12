package com.banco.api.dto.user.request.modification;

import java.io.Serializable;

public class UserModificationRequest implements Serializable{
	private String username;
	private String oldUsername;
    private String address;
    private String phone;
   
    public UserModificationRequest() {}

	public String getUsername() {
		return username;
	}

	public String getOldUsername() {
		return oldUsername;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	@Override
	public String toString() {
		return "UserModificationRequest [username=" + username + ", oldUsername=" + oldUsername + ", address=" + address
				+ ", phone=" + phone + "]";
	}
}
