package com.banco.api.dto.request;

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

    public String getCuitCuilCdi() {
        return cuitCuilCdi;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }


    public boolean isWithCheckingAccount() {
        return withCheckingAccount;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "username='" + username + '\'' +
                ", cuitCuilCdi='" + cuitCuilCdi + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", withCheckingAccount=" + withCheckingAccount +
                '}';
    }
}
