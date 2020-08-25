package com.banco.api.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="services")
public class ServiceEntity {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int idService;
	private String name;
	private float amount;
	@Temporal(TemporalType.TIMESTAMP)	
	private Date due;
	
	public ServiceEntity(int idService, String name, float amount, Date due) {
		super();
		this.idService = idService;
		this.name = name;
		this.amount = amount;
		this.due = due;
	}

	public int getIdService() {
		return idService;
	}

	public void setIdService(int idService) {
		this.idService = idService;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Date getDue() {
		return due;
	}

	public void setDue(Date due) {
		this.due = due;
	}	
}
