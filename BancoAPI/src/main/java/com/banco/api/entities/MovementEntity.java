package com.banco.api.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.banco.api.model.MovementType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
@Table(name="movements")
public class MovementEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int idMovement;
	
	private int movementType;
	
	@Temporal(TemporalType.TIMESTAMP)	
	private LocalDateTime dayAndHour;
	
	private String concept;
	private float amount;
	private int transactionNumber;
	@OneToOne
	@JoinColumn(name="idService")
	@Column(name="idService")
	private ServiceEntity service;
	
	@ManyToOne
	@JoinColumn (name="idCheckingAccount")
	@Column (name="idChEntryAccount")
	private CheckingEntity chEntryAccount;
	
	@ManyToOne
	@JoinColumn (name="idCheckingAccount")
	@Column (name="idChExitAccount")
	private CheckingEntity chExitAccount;
	
	@ManyToOne
	@JoinColumn (name="idSavingsAccount")
	@Column (name="idSaEntryAccount")
	private SavingsEntity saEntryAccount;
	
	@ManyToOne
	@JoinColumn (name="idSavingsAccount")
	@Column (name="idSaExitAccount")
	private SavingsEntity saExitAccount;
	
	private float entryBalanceBeforeMovement;
	private float exitBalanceBeforeMovement;
	public MovementEntity(int idMovement, int movementType, LocalDateTime dayAndHour, String concept, float amount,
			int transactionNumber, ServiceEntity service, CheckingEntity chEntryAccount, CheckingEntity chExitAccount,
			SavingsEntity saEntryAccount, SavingsEntity saExitAccount, float entryBalanceBeforeMovement,
			float exitBalanceBeforeMovement) {
		super();
		this.idMovement = idMovement;
		this.movementType = movementType;
		this.dayAndHour = dayAndHour;
		this.concept = concept;
		this.amount = amount;
		this.transactionNumber = transactionNumber;
		this.service = service;
		this.chEntryAccount = chEntryAccount;
		this.chExitAccount = chExitAccount;
		this.saEntryAccount = saEntryAccount;
		this.saExitAccount = saExitAccount;
		this.entryBalanceBeforeMovement = entryBalanceBeforeMovement;
		this.exitBalanceBeforeMovement = exitBalanceBeforeMovement;
	}
	public int getIdMovement() {
		return idMovement;
	}
	public void setIdMovement(int idMovement) {
		this.idMovement = idMovement;
	}
	public MovementType getMovementType() {
		MovementType mt = null;
		if(movementType == 0) {
			mt = MovementType.Deposit;
		}
		if(movementType == 1) {
			mt = MovementType.Extraction;
		}
		if(movementType == 2) {
			mt = MovementType.CommissionOrMaintenance;
		}
		if(movementType == 3) {
			mt = MovementType.ServicesPay;
		}
		return mt;
	}
	public void setMovementType(MovementType mt) {
		if(MovementType.Deposit == mt) {
			this.movementType = 0;
		}
		if(MovementType.Extraction == mt) {
			this.movementType = 1;
		}
		if(MovementType.CommissionOrMaintenance == mt) {
			this.movementType = 2;
		}
		if(MovementType.ServicesPay == mt) {
			this.movementType = 3;
		}
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
	public ServiceEntity getService() {
		return service;
	}
	public void setService(ServiceEntity service) {
		this.service = service;
	}
	public CheckingEntity getChEntryAccount() {
		return chEntryAccount;
	}
	public void setChEntryAccount(CheckingEntity chEntryAccount) {
		this.chEntryAccount = chEntryAccount;
	}
	public CheckingEntity getChExitAccount() {
		return chExitAccount;
	}
	public void setChExitAccount(CheckingEntity chExitAccount) {
		this.chExitAccount = chExitAccount;
	}
	public SavingsEntity getSaEntryAccount() {
		return saEntryAccount;
	}
	public void setSaEntryAccount(SavingsEntity saEntryAccount) {
		this.saEntryAccount = saEntryAccount;
	}
	public SavingsEntity getSaExitAccount() {
		return saExitAccount;
	}
	public void setSaExitAccount(SavingsEntity saExitAccount) {
		this.saExitAccount = saExitAccount;
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
}
