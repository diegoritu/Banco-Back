package com.banco.api.model;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class Movement {
	private int idMovement;
	private MovementType movementType;
	private LocalDateTime dayAndHour;
	private String concept;
	private float amount;
	private int transactionNumber;
	private Service service;
	private Account exitAccount;
	private Account entryAccount;
	private String reference;
	
	public Movement(int idMovement, MovementType movementType, LocalDateTime dayAndHour, String concept, float amount,
			int transactionNumber, Service service, Account exitAccount, Account entryAccount, String reference) {
		super();
		this.idMovement = idMovement;
		this.movementType = movementType;
		this.dayAndHour = dayAndHour;
		this.concept = concept;
		this.amount = amount;
		this.transactionNumber = transactionNumber;
		this.service = service;
		this.exitAccount = exitAccount;
		this.entryAccount = entryAccount;
		this.reference = reference;
	}
	public int getIdMovement() {
		return idMovement;
	}
	public void setIdMovement(int idMovement) {
		this.idMovement = idMovement;
	}
	public MovementType getMovementType() {
		return movementType;
	}
	public void setMovementType(MovementType movementType) {
		this.movementType = movementType;
	}
	public LocalDateTime getDayAndHour() {
		return dayAndHour;
	}
	public void setDayAndHour(LocalDateTime dayAndHour) {
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
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	public Account getExitAccount() {
		return exitAccount;
	}
	public void setExitAccount(Account exitAccount) {
		this.exitAccount = exitAccount;
	}
	public Account getEntryAccount() {
		return entryAccount;
	}
	public void setEntryAccount(Account entryAccount) {
		this.entryAccount = entryAccount;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	
}
