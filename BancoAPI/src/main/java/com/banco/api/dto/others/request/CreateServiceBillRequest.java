package com.banco.api.dto.others.request;

import java.io.Serializable;

public class CreateServiceBillRequest implements Serializable {
	private String name;
	private float amount;
	private String vendorUsername;
	private String dueDate;
	private String vendorAccountType;
	private String vendorAccountNumber;
	private int amountOfIds;

    public CreateServiceBillRequest() {}

	public CreateServiceBillRequest(String name, float amount,
									String vendorUsername, String dueDate, String vendorAccountType, String vendorAccountNumber, int amountOfIds) {
		super();
		this.name = name;
		this.amount = amount;
		this.vendorUsername = vendorUsername;
		this.dueDate = dueDate;
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

	public String getVendorUsername() {
		return vendorUsername;
	}

	public String getDueDate() {
		return dueDate;
	}

	public String getVendorAccountType() {
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
		return "CreateServiceBillRequest [name=" + name + ", amount=" + amount + ", vendorUsername=" + vendorUsername + ", dueDate=" + dueDate + ", vendorAccountType="
				+ vendorAccountType + ", vendorAccountNumber=" + vendorAccountNumber + ", amountOfIds=" + amountOfIds
				+ "]";
	}

	

}
