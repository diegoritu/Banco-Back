package com.banco.api.dto.others.request;

import java.io.Serializable;

import com.banco.api.dto.user.LegalUserDTO;
import com.banco.api.dto.user.PhysicalUserDTO;

public class CreateServiceRequest implements Serializable {
	private String name;
	private float amount;
	private boolean regular;
	private String vendorUsername;
	private String dueDay;
	private int vendorAccountType;
	private String vendorAccountNumber;
	private int amountOfIds;
	
    public CreateServiceRequest() {}

	public CreateServiceRequest(String name, float amount, boolean regular,
			String vendorUsername, String dueDay, int vendorAccountType, String vendorAccountNumber, int amountOfIds) {
		super();
		this.name = name;
		this.amount = amount;
		this.regular = regular;
		this.vendorUsername = vendorUsername;
		this.dueDay = dueDay;
		this.vendorAccountType = vendorAccountType;
		this.vendorAccountNumber = vendorAccountNumber;
		this.amountOfIds = amountOfIds;
	}

	public String getName() {
		return name;
	}

	public float getAmount() {
		return amount;
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

	public int getAmountOfIds() {
		return amountOfIds;
	}

	@Override
	public String toString() {
		return "CreateServiceRequest [name=" + name + ", amount=" + amount + ", regular=" + regular
				+ ", vendorUsername=" + vendorUsername + ", dueDay=" + dueDay + ", vendorAccountType="
				+ vendorAccountType + ", vendorAccountNumber=" + vendorAccountNumber + ", amountOfIds=" + amountOfIds
				+ "]";
	}

	

}
