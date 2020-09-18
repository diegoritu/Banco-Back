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
	private int vendorAccountType;
	private String vendorAccountNumber;
	
    public CreateServiceRequest() {}

	public CreateServiceRequest(String name, float amount, String idServicePayment, boolean regular, String vendorUsername,
			String dueDay, int vendorAccountType, String vendorAccountNumber) {
		super();
		this.name = name;
		this.amount = amount;
		this.idServicePayment = idServicePayment;
		this.regular = regular;
		this.vendorUsername = vendorUsername;
		this.dueDay = dueDay;
		this.vendorAccountType = vendorAccountType;
		this.vendorAccountNumber = vendorAccountNumber;
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

	public int getVendorAccountType() {
		return vendorAccountType;
	}
	public String getVendorAccountNumber() {
		return vendorAccountNumber;
	}

	@Override
	public String toString() {
		return "CreateServiceRequest [name=" + name + ", amount=" + amount + ", idServicePayment=" + idServicePayment
				+ ", regular=" + regular + ", vendorUsername=" + vendorUsername + ", dueDay=" + dueDay
				+ ", vendorAccountType=" + vendorAccountType + ", vendorAccountNumber=" + vendorAccountNumber + "]";
	}

}
