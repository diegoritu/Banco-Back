package com.banco.api.dto.user;

public class AdministrativeUserDTO extends UserDTO {

    private String dni;
    private String birthDate;
    private String firstName;
    private String lastName;
    private String mobilePhone;
    private boolean firstLogin;
    private String password;

    public AdministrativeUserDTO() {
    }

	public AdministrativeUserDTO(String dni, String birthDate, String firstName, String lastName, String mobilePhone,
			boolean firstLogin, String password) {
		super();
		this.dni = dni;
		this.birthDate = birthDate;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobilePhone = mobilePhone;
		this.firstLogin = firstLogin;
		this.password = password;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "AdministrativeUserDTO [dni=" + dni + ", birthDate=" + birthDate + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", mobilePhone=" + mobilePhone + ", firstLogin=" + firstLogin
				+ ", password=" + password + "]";
	}
}
