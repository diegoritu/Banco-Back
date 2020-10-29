package com.banco.api.dto.others;

import java.util.Collection;

public class ServiceCreatedDTO {
	private Collection<String> repeatedIds;
	private String vendorId;
	public ServiceCreatedDTO() {
		super();
	}
	
	public ServiceCreatedDTO(Collection<String> ids, String vendorId) {
		super();
		this.repeatedIds = ids;
		this.vendorId = vendorId;
	}

	public Collection<String> getIds() {
		return repeatedIds;
	}
	public String getVendorId() {
		return vendorId;
	}

	public void setIds(Collection<String> ids) {
		this.repeatedIds = ids;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	
}
