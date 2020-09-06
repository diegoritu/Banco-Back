package com.banco.api.model;

import com.banco.api.model.account.Account;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;


@Entity
@Table(name = "movements")
public class Movement {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMovement;
	
	/*	Database meanings for movementType:
	 * DEPOSIT = 0
	 * EXTRACTION = 1
	 * COMMISSION = 2
	 * MAINTENANCE = 3
	 * SERVICES_PAYMENT = 4
	 */
    private int movementType;
    
    @Column
    @Type(type="timestamp")
    private LocalDateTime dayAndHour;
    private String concept;
    private float amount;
    private int transactionNumber;
    
    //@Column(name = "idService")
    @OneToOne
    @JoinColumn(name = "idService")
    private Service service;
    
    private String reference;
    private float entryBalanceBeforeMovement;
    private float exitBalanceBeforeMovement;
   
   // @Column(name = "idEntryAccount")
    @ManyToOne
    private Checking chEntryAccount;
    @ManyToOne
    private Checking chExitAccount;
    
    @ManyToOne
    @JoinColumn(name = "idEntryAccount")
    private Account entryAccount;

    //   @Column(name = "idExitAccount")
    @ManyToOne
    @JoinColumn(name = "idExitAccount")
    private Account exitAccount;
    
    @ManyToOne
    private Savings saEntryAccount;
    @ManyToOne
    private Savings saExitAccount;

    /*
     * Constructor without idMovement
     */
    
    public Movement(int movementType, LocalDateTime dayAndHour, String concept, float amount, int transactionNumber,
			Service service, String reference, float entryBalanceBeforeMovement, float exitBalanceBeforeMovement,
			Checking chEntryAccount, Checking chExitAccount, Savings saEntryAccount, Savings saExitAccount) {
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

    /*
     * Constructor with idMovement
     */

    public Movement(int idMovement, int movementType, LocalDateTime dayAndHour, String concept, float amount,
			int transactionNumber, Service service, String reference, float entryBalanceBeforeMovement,
			float exitBalanceBeforeMovement, Checking chEntryAccount, Checking chExitAccount, Savings saEntryAccount,
			Savings saExitAccount) {
		super();
		this.idMovement = idMovement;
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

    public Checking getChEntryAccount() {
        return chEntryAccount;
    }

    public void setChEntryAccount(Checking chEntryAccount) {
        this.chEntryAccount = chEntryAccount;
    }

    public Checking getChExitAccount() {
        return chExitAccount;
    }

    public void setChExitAccount(Checking chExitAccount) {
        this.chExitAccount = chExitAccount;
    }

    public Savings getSaEntryAccount() {
        return saEntryAccount;
    }

    public void setSaEntryAccount(Savings saEntryAccount) {
        this.saEntryAccount = saEntryAccount;
    }

    public Savings getSaExitAccount() {
        return saExitAccount;
    }

    public void setSaExitAccount(Savings saExitAccount) {
        this.saExitAccount = saExitAccount;
    }

    @Override
    public String toString() {
        return "Movement{" +
                "idMovement=" + idMovement +
                ", movementType=" + movementType +
                ", dayAndHour=" + dayAndHour +
                ", concept='" + concept + '\'' +
                ", amount=" + amount +
                ", transactionNumber=" + transactionNumber +
                ", service=" + service +
                ", reference='" + reference + '\'' +
                ", entryBalanceBeforeMovement=" + entryBalanceBeforeMovement +
                ", exitBalanceBeforeMovement=" + exitBalanceBeforeMovement +
                ", chEntryAccount=" + chEntryAccount +
                ", chExitAccount=" + chExitAccount +
                ", saEntryAccount=" + saEntryAccount +
                ", saExitAccount=" + saExitAccount +
                '}';
    }
}
