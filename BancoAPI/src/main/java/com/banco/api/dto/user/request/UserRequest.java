package com.banco.api.dto.user.request;

import java.io.Serializable;

public class UserRequest implements Serializable {

    private String username;
    private String cuitCuilCdi;
    private String address;
    private String phone;
    private boolean withCheckingAccount;
    
    public UserRequest() {
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCuitCuilCdi() {
		return cuitCuilCdi;
	}

	public void setCuitCuilCdi(String cuitCuilCdi) {
		this.cuitCuilCdi = cuitCuilCdi;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isWithCheckingAccount() {
		return withCheckingAccount;
	}

	public void setWithCheckingAccount(boolean withCheckingAccount) {
		this.withCheckingAccount = withCheckingAccount;
	}
	
	@Override
	public String toString() {
		return "UserRequest [username=" + username + ", cuitCuilCdi=" + cuitCuilCdi + ", address=" + address
				+ ", phone=" + phone + ", withCheckingAccount=" + withCheckingAccount +  "]";
	}

    
}
