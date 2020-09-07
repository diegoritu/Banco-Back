package com.banco.api.dto.others.request;

import java.io.Serializable;

import com.banco.api.dto.account.AccountDTO;
import com.banco.api.dto.account.CheckingDTO;
import com.banco.api.dto.account.SavingsDTO;
import com.banco.api.dto.others.ServiceDTO;

public class MovementRequest implements Serializable{
	private int movementType;
	private String dayAndHour;
	private String concept;
	private float amount;
    //private int transactionNumber;
    private String reference;
    private String accountNumberEntryAccount;
    private String accountNumberExitAccount;
    /*private float entryBalanceBeforeMovement;
    private float exitBalanceBeforeMovement;
    */
    public MovementRequest() {}


	public MovementRequest(int movementType, String dayAndHour, String concept, float amount, /*int transactionNumber,*/
			String reference, String accountNumberEntryAccount, String accountNumberExitAccount/*,
			float entryBalanceBeforeMovement, float exitBalanceBeforeMovement*/) {
		super();
		this.movementType = movementType;
		this.dayAndHour = dayAndHour;
		this.concept = concept;
		this.amount = amount;
		//this.transactionNumber = transactionNumber;
		this.reference = reference;
		this.accountNumberEntryAccount = accountNumberEntryAccount;
		this.accountNumberExitAccount = accountNumberExitAccount;
		/*this.entryBalanceBeforeMovement = entryBalanceBeforeMovement;
		this.exitBalanceBeforeMovement = exitBalanceBeforeMovement;*/
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

	/*public int getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(int transactionNumber) {
		this.transactionNumber = transactionNumber;
	}*/

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getAccountNumberEntryAccount() {
		return accountNumberEntryAccount;
	}

	public void setAccountNumberEntryAccount(String accountNumberEntryAccount) {
		this.accountNumberEntryAccount = accountNumberEntryAccount;
	}

	public String getAccountNumberExitAccount() {
		return accountNumberExitAccount;
	}

	public void setAccountNumberExitAccount(String accountNumberExitAccount) {
		this.accountNumberExitAccount = accountNumberExitAccount;
	}

	/*public float getEntryBalanceBeforeMovement() {
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
	}*/


	@Override
	public String toString() {
		return "MovementRequest [movementType=" + movementType + ", dayAndHour=" + dayAndHour + ", concept=" + concept
				+ ", amount=" + amount + ", transactionNumber=" /*+ transactionNumber*/ + ", reference=" + reference
				+ ", accountNumberEntryAccount=" + accountNumberEntryAccount + ", accountNumberExitAccount="
				+ accountNumberExitAccount + ", entryBalanceBeforeMovement="/* + entryBalanceBeforeMovement
				+ ", exitBalanceBeforeMovement=" + exitBalanceBeforeMovement*/ + "]";
	}

    
}
