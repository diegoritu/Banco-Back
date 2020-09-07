package com.banco.api.dto.others.request;

import java.io.Serializable;

public class ServiceRequest implements Serializable {
	private String name;
	private float amount;
    private String due;
    
    public ServiceRequest() {}

	public ServiceRequest(String name, float amount, String due) {
		super();
		this.name = name;
		this.amount = amount;
		this.due = due;
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

	public String getDue() {
		return due;
	}

	public void setDue(String due) {
		this.due = due;
	}

	@Override
	public String toString() {
		return "ServiceRequest [name=" + name + ", amount=" + amount + ", due=" + due + "]";
	}
	
	
}
