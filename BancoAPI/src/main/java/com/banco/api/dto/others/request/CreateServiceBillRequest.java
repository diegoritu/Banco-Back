package com.banco.api.dto.others.request;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class CreateServiceBillRequest implements Serializable {
	private MultipartFile file;
	private String name;
	private String vendorUsername;
	private String vendorAccountType;

    public CreateServiceBillRequest() {}

	public CreateServiceBillRequest(MultipartFile file, String name, String vendorUsername, String vendorAccountType) {
		super();
		this.file = file;
		this.name = name;
		this.vendorUsername = vendorUsername;
		this.vendorAccountType = vendorAccountType;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVendorUsername() {
		return vendorUsername;
	}

	public void setVendorUsername(String vendorUsername) {
		this.vendorUsername = vendorUsername;
	}

	public String getVendorAccountType() {
		return vendorAccountType;
	}

	public void setVendorAccountType(String vendorAccountType) {
		this.vendorAccountType = vendorAccountType;
	}

	@Override
	public String toString() {
		return "CreateServiceBillRequest [file=" + file + ", name=" + name + ", vendorUsername=" + vendorUsername
				+ ", vendorAccountType=" + vendorAccountType + "]";
	}

	
	

}
