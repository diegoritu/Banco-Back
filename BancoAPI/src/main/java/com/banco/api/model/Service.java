package com.banco.api.model;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Service {
	private String name;
	private float amount;
	private Date due;
	private int idService;
	
	public Service(String name, float amount, Date due, int idService) {
		super();
		this.name = name;
		this.amount = amount;
		this.due = due;
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

	public int getIdService() {
		return idService;
	}

	public void setIdService(int idService) {
		this.idService = idService;
	}
}
