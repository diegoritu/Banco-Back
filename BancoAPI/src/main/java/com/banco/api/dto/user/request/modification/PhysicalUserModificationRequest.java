package com.banco.api.dto.user.request.modification;


public class PhysicalUserModificationRequest extends UserModificationRequest {
	private String firstName;
    private String lastName;
    private String birthDate;
    private String mobilePhone;

    public PhysicalUserModificationRequest() {}

	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}

	@Override
	public String toString() {
		return "PhysicalUserModificationRequest [firstName=" + firstName + ", lastName=" + lastName + ", birthDate="
				+ birthDate + ", mobilePhone=" + mobilePhone + "]";
	}

}
