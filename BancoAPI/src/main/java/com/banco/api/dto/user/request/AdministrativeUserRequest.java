package com.banco.api.dto.user.request;

public class AdministrativeUserRequest extends UserRequest{
	private String firstName;
    private String lastName;
    private String birthDate;
    private String dni;
    private String mobilePhone;
    
	public AdministrativeUserRequest() {}

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

	@Override
	public String toString() {
		return "AdministrativeUserRequest [firstName=" + firstName + ", lastName=" + lastName + ", birthDate="
				+ birthDate + ", dni=" + dni + ", mobilePhone=" + mobilePhone + "]";
	}
	
}
