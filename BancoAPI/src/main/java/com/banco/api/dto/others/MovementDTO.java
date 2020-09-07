package com.banco.api.dto.others;


import com.banco.api.dto.account.AccountDTO;
import com.banco.api.dto.account.CheckingDTO;
import com.banco.api.dto.account.SavingsDTO;


public class MovementDTO {
	private int movementType;
	private String dayAndHour;
	private String concept;
	private float amount;
    private int transactionNumber;
    private ServiceDTO service;
    private String reference;
    private float entryBalanceBeforeMovement;
    private float exitBalanceBeforeMovement;
    private CheckingDTO chEntryAccount;
    private CheckingDTO chExitAccount;
    private SavingsDTO saEntryAccount;
    private SavingsDTO saExitAccount;
    
    public MovementDTO() {}

	public MovementDTO(int movementType, String dayAndHour, String concept, float amount,
			int transactionNumber, ServiceDTO service, String reference, float entryBalanceBeforeMovement,
			float exitBalanceBeforeMovement, CheckingDTO chEntryAccount, CheckingDTO chExitAccount,
			AccountDTO entryAccount, AccountDTO exitAccount, SavingsDTO saEntryAccount, SavingsDTO saExitAccount) {
		super();
		this.movementType = movementType;
		this.dayAndHour = dayAndHour;
		this.concept = concept;
		this.amount = amount;
		this.transactionNumber = transactionNumber;
		this.service = service;
		this.reference = reference;
		this.entryBalanceBeforeMovement = entryBalanceBeforeMovement;
		this.exitBalanceBeforeMovement = exitBalanceBeforeMovement;
		this.chEntryAccount = chEntryAccount;
		this.chExitAccount = chExitAccount;
		this.saEntryAccount = saEntryAccount;
		this.saExitAccount = saExitAccount;
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

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public int getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(int transactionNumber) {
		this.transactionNumber = transactionNumber;
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

	public float getEntryBalanceBeforeMovement() {
		return entryBalanceBeforeMovement;
	}

	public void setEntryBalanceBeforeMovement(float entryBalanceBeforeMovement) {
		this.entryBalanceBeforeMovement = entryBalanceBeforeMovement;
	}

	public float getExitBalanceBeforeMovement() {
		return exitBalanceBeforeMovement;
	}

	public void setExitBalanceBeforeMovement(float exitBalanceBeforeMovement) {
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

	@Override
	public String toString() {
		return "MovementDTO [movementType=" + movementType + ", dayAndHour=" + dayAndHour + ", concept=" + concept
				+ ", amount=" + amount + ", transactionNumber=" + transactionNumber + ", service=" + service
				+ ", reference=" + reference + ", entryBalanceBeforeMovement=" + entryBalanceBeforeMovement
				+ ", exitBalanceBeforeMovement=" + exitBalanceBeforeMovement + ", chEntryAccount=" + chEntryAccount
				+ ", chExitAccount=" + chExitAccount + ", saEntryAccount=" + saEntryAccount + ", saExitAccount=" + saExitAccount + "]";
	}
	
	
}
