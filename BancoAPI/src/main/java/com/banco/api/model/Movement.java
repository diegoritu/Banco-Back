package com.banco.api.model;

import com.banco.api.adapter.Externalizable;
import com.banco.api.dto.movement.MovementDTO;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "movements")
@SequenceGenerator(name="transactionId", initialValue=10000, allocationSize=100)
public class Movement implements Externalizable<MovementDTO> {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMovement;
	
	/*	Database meanings for movementType:
	 * DEPOSIT = 0
	 * EXTRACTION = 1
	 * COMMISSION = 2
	 * MAINTENANCE = 3
	 * SERVICES_PAYMENT = 4
	 * TRANSFER_BETWEEN_OWN_ACCOUNTS = 5
	 * TRANSFER_TO_OTHER_ACCOUNTS = 6
	 * INTERESTS = 7
	 * DEBIT_CARD_PAYMENT = 8
	 * CREDIT_ENTITY_DEBIT_CLIENT = 9
	 */
    private int movementType;
    
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date dayAndHour;
    private String concept;
    private float amount;
        
    @OneToOne
    @JoinColumn(name = "idService")
    private ServicePayment service;
    
    private String reference;
    private float entryBalanceBeforeMovement;
    private float exitBalanceBeforeMovement;
   
    @ManyToOne
    private Checking chEntryAccount;
    @ManyToOne
    private Checking chExitAccount;
    @ManyToOne
    private Savings saEntryAccount;
    @ManyToOne
    private Savings saExitAccount;

    private String businessName;
    /*
     * Constructor without idMovement and transactionId
     */
    
    public Movement(int movementType, Date dayAndHour, String concept, float amount,
			ServicePayment service, String reference, float entryBalanceBeforeMovement, float exitBalanceBeforeMovement,
			Checking chEntryAccount, Checking chExitAccount, Savings saEntryAccount, Savings saExitAccount, String businessName) {
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
		this.businessName = businessName;
	}

    /*
     * Constructor with idMovement and transactionId
     */

    public Movement(int idMovement, int movementType, Date dayAndHour, String concept, float amount, ServicePayment service, String reference, float entryBalanceBeforeMovement,
			float exitBalanceBeforeMovement, Checking chEntryAccount, Checking chExitAccount, Savings saEntryAccount,
			Savings saExitAccount, String businessName) {
		super();
		this.idMovement = idMovement;
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
		this.businessName = businessName;
	}


    public Movement() {}

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

    public Date getDayAndHour() {
        return dayAndHour;
    }

    public void setDayAndHour(Date dayAndHour) {
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

    public ServicePayment getService() {
        return service;
    }

    public void setService(ServicePayment service) {
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
    
    public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Override
	public String toString() {
		return "Movement [idMovement=" + idMovement + ", movementType=" + movementType + ", dayAndHour=" + dayAndHour
				+ ", concept=" + concept + ", amount=" + amount + ", service=" + service + ", reference=" + reference
				+ ", entryBalanceBeforeMovement=" + entryBalanceBeforeMovement + ", exitBalanceBeforeMovement=" 
				+ exitBalanceBeforeMovement + ", chEntryAccount=" + chEntryAccount + ", chExitAccount=" 
				+ chExitAccount + ", saEntryAccount=" + saEntryAccount + ", saExitAccount="	+ saExitAccount 
				+ ", businessName=" + businessName + "]";
	}

	@Override
	public MovementDTO toView() {
		MovementDTO result = new MovementDTO();
		result.setAmount(this.getAmount());
		result.setConcept(this.getConcept());
		result.setBusinessName(this.getBusinessName());
		if(this.getChEntryAccount() != null) {
			result.setChEntryAccount(this.getChEntryAccount().toView());
		}
		if(this.getChExitAccount() != null) {
			result.setChExitAccount(this.getChExitAccount().toView());
		}
		if(this.getSaEntryAccount() != null) {
			result.setSaEntryAccount(this.getSaEntryAccount().toView());
		}
		if(this.getSaExitAccount() != null) {
			result.setSaExitAccount(this.getSaExitAccount().toView());
		}
		if(this.getService() != null) {
			result.setService(this.getService().toView());
		}
		result.setDayAndHour(this.getDayAndHour().toString());
		result.setMovementType(this.getMovementType());
		result.setEntryBalanceBeforeMovement(this.getEntryBalanceBeforeMovement());
		result.setExitBalanceBeforeMovement(this.getExitBalanceBeforeMovement());
		result.setIdMovement(idMovement);
		result.setReference(this.getReference());
		return result;
	}
}
