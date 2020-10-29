package com.banco.api.dto.user;

import com.banco.api.dto.account.CheckingDTO;
import com.banco.api.dto.account.SavingsDTO;
import com.banco.api.dto.others.DebitCardDTO;
import com.banco.api.dto.user.UserDTO;

public class PhysicalUserDTO extends UserDTO {

    private String dni;
    private SavingsDTO savings; //Caja de Ahorro
    private CheckingDTO checking; //Cuenta Corriente
    private DebitCardDTO debitCard;
    private String birthDate;
    private String firstName;
    private String lastName;
    private String mobilePhone;
    private boolean firstLogin;
    private String password;

    public PhysicalUserDTO() {
    }

	public PhysicalUserDTO(String dni, SavingsDTO savings, CheckingDTO checking, DebitCardDTO debitCard,
			String birthDate, String firstName, String lastName, String mobilePhone, boolean firstLogin,
			String password) {
		super();
		this.dni = dni;
		this.savings = savings;
		this.checking = checking;
		this.debitCard = debitCard;
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

	public SavingsDTO getSavings() {
		return savings;
	}

	public void setSavings(SavingsDTO savings) {
		this.savings = savings;
	}

	public CheckingDTO getChecking() {
		return checking;
	}

	public void setChecking(CheckingDTO checking) {
		this.checking = checking;
	}

	public DebitCardDTO getDebitCard() {
		return debitCard;
	}

	public void setDebitCard(DebitCardDTO debitCard) {
		this.debitCard = debitCard;
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
		return "PhysicalUserDTO [dni=" + dni + ", savings=" + savings + ", checking=" + checking + ", debitCard="
				+ debitCard + ", birthDate=" + birthDate + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", mobilePhone=" + mobilePhone + ", firstLogin=" + firstLogin + ", password=" + password + "]";
	}

}
