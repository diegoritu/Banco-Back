package com.banco.api.dto.others.request;

import java.io.Serializable;

import com.banco.api.dto.user.LegalUserDTO;
import com.banco.api.dto.user.PhysicalUserDTO;

public class CreateServiceRequest implements Serializable {
	private String name;
	private float amount;
	private String idServicePayment;
	private boolean regular;
	private String vendorUsername;
	private String dueDay;
	
    public CreateServiceRequest() {}

	public CreateServiceRequest(String name, float amount, String idServicePayment, boolean regular, String vendorUsername,
			String dueDay) {
		super();
		this.name = name;
		this.amount = amount;
		this.idServicePayment = idServicePayment;
		this.regular = regular;
		this.vendorUsername = vendorUsername;
		this.dueDay = dueDay;
	}

	public String getName() {
		return name;
	}

	public float getAmount() {
		return amount;
	}

	public String getIdServicePayment() {
		return idServicePayment;
	}

	public boolean isRegular() {
		return regular;
	}

	public String getVendorUsername() {
		return vendorUsername;
	}

	public String getDueDay() {
		return dueDay;
	}
}
