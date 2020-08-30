package com.banco.api.dto.user.request;

public class PhysicalUserRequest extends UserRequest {

    private String firstName;
    private String lastName;
    private String birthDate;
    private String dni;
    private String mobilePhone;

    public PhysicalUserRequest() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getDni() {
        return dni;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }
}
