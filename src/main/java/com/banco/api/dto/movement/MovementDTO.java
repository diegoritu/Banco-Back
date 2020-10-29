package com.banco.api.dto.movement;


import com.banco.api.dto.account.AccountDTO;
import com.banco.api.dto.account.CheckingDTO;
import com.banco.api.dto.account.SavingsDTO;
import com.banco.api.dto.others.ServiceDTO;


public class MovementDTO {
	private int movementType;
	private String dayAndHour;
	private String concept;
	private Float amount;
    private ServiceDTO service;
    private String reference;
    private Float entryBalanceBeforeMovement;
    private Float exitBalanceBeforeMovement;
    private CheckingDTO chEntryAccount;
    private CheckingDTO chExitAccount;
    private SavingsDTO saEntryAccount;
    private SavingsDTO saExitAccount;
    private int idMovement;
    private String businessName;
    
    public MovementDTO() {}

	public MovementDTO(int movementType, String dayAndHour, String concept, Float amount,
			ServiceDTO service, String reference, Float entryBalanceBeforeMovement,
			float exitBalanceBeforeMovement, CheckingDTO chEntryAccount, CheckingDTO chExitAccount,
			AccountDTO entryAccount, AccountDTO exitAccount, SavingsDTO saEntryAccount, SavingsDTO saExitAccount, int idMovement, String businessName) {
		super();
		this.movementType = movementType;
		this.dayAndHour = dayAndHour;
		this.concept = concept;
		this.amount = amount;
		this.service = service;
		this.reference = reference;
		this.entryBalanceBeforeMovement = entryBalanceBeforeMovement;
		this.exitBalanceBeforeMovement = exitBalanceBeforeMovement;
		this.chEntryAccount = chEntryAccount;
		this.chExitAccount = chExitAccount;
		this.saEntryAccount = saEntryAccount;
		this.saExitAccount = saExitAccount;
		this.idMovement = idMovement;
		this.businessName = businessName;
	}

	
	public int getIdMovement() {
		return idMovement;
	}

	public void setIdMovement(int idMovement) {
		this.idMovement = idMovement;
	}

	public int getMovementType() {
		return movementType;
	}

	public void setMovementType(int movementType) {
		this.movementType = movementType;
	}

	public String getDayAndHour() {
		return dayAndHour;
	}

	public void setDayAndHour(String dayAndHour) {
		this.dayAndHour = dayAndHour;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public ServiceDTO getService() {
		return service;
	}

	public void setService(ServiceDTO service) {
		this.service = service;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Float getEntryBalanceBeforeMovement() {
		return entryBalanceBeforeMovement;
	}

	public void setEntryBalanceBeforeMovement(Float entryBalanceBeforeMovement) {
		this.entryBalanceBeforeMovement = entryBalanceBeforeMovement;
	}

	public Float getExitBalanceBeforeMovement() {
		return exitBalanceBeforeMovement;
	}

	public void setExitBalanceBeforeMovement(Float exitBalanceBeforeMovement) {
		this.exitBalanceBeforeMovement = exitBalanceBeforeMovement;
	}

	public CheckingDTO getChEntryAccount() {
		return chEntryAccount;
	}

	public void setChEntryAccount(CheckingDTO chEntryAccount) {
		this.chEntryAccount = chEntryAccount;
	}

	public CheckingDTO getChExitAccount() {
		return chExitAccount;
	}

	public void setChExitAccount(CheckingDTO chExitAccount) {
		this.chExitAccount = chExitAccount;
	}

	public SavingsDTO getSaEntryAccount() {
		return saEntryAccount;
	}

	public void setSaEntryAccount(SavingsDTO saEntryAccount) {
		this.saEntryAccount = saEntryAccount;
	}

	public SavingsDTO getSaExitAccount() {
		return saExitAccount;
	}

	public void setSaExitAccount(SavingsDTO saExitAccount) {
		this.saExitAccount = saExitAccount;
	}
	
	public String getBusinessName() {
		return businessName;
	}
	
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Override
	public String toString() {
		return "MovementDTO [movementType=" + movementType + ", dayAndHour=" + dayAndHour + ", concept=" + concept
				+ ", amount=" + amount + ", service=" + service + ", reference="
				+ reference + ", entryBalanceBeforeMovement=" + entryBalanceBeforeMovement
				+ ", exitBalanceBeforeMovement=" + exitBalanceBeforeMovement + ", chEntryAccount=" + chEntryAccount
				+ ", chExitAccount=" + chExitAccount + ", saEntryAccount=" + saEntryAccount + ", saExitAccount="
				+ saExitAccount + ", idMovement=" + idMovement + ", businessName=" + businessName + "]";
	}
	
}
