package com.banco.api.dto.user;

import com.banco.api.dto.account.CheckingDTO;
import com.banco.api.dto.account.SavingsDTO;
import com.banco.api.dto.others.DebitCardDTO;

public class LegalUserDTO extends UserDTO {

    private String businessName;
    private SavingsDTO savings; //Caja de Ahorro
    private CheckingDTO checking; //Cuenta Corriente
    private DebitCardDTO debitCard;
    private boolean firstLogin;
    private String password;
    
	public LegalUserDTO(String businessName, SavingsDTO savings, CheckingDTO checking, DebitCardDTO debitCard, boolean firstLogin,
			String password) {
		super();
		this.businessName = businessName;
		this.savings = savings;
		this.checking = checking;
		this.debitCard = debitCard;
		this.firstLogin = firstLogin;
		this.password = password;
	}
	
	public LegalUserDTO() {
		// TODO Auto-generated constructor stub
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
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
		return "LegalUserDTO [businessName=" + businessName + ", savings=" + savings + ", checking=" + checking
				+ ", debitCard=" + debitCard + ", firstLogin=" + firstLogin + ", password=" + password + "]";
	}
}
