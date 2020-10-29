package com.banco.api.dto.others;

import java.util.Date;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

public class ServiceCsvDTO {
	@CsvBindByPosition(position = 0)
	private String servicePaymentId;
	@CsvBindByPosition(position = 1)
	@CsvDate(value = "yyyy-MM-dd")
	private Date due;
	@CsvBindByPosition(position = 2)
	private float amount;
	@CsvBindByPosition(position = 3)
	private String cuitCuilCdi;
	
	
	public ServiceCsvDTO() {}
	
	public ServiceCsvDTO(String cuitCuilCdi, float amount, Date due, String servicePaymentId) {
		super();
		this.cuitCuilCdi = cuitCuilCdi;
		this.amount = amount;
		this.due = due;
		this.servicePaymentId = servicePaymentId;
	}
	
	
	public String getCuitCuilCdi() {
		return cuitCuilCdi;
	}
	public void setCuitCuilCdi(String cuitCuilCdi) {
		this.cuitCuilCdi = cuitCuilCdi;
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
	public String getServicePaymentId() {
		return servicePaymentId;
	}
	public void setServicePaymentId(String servicePaymentId) {
		this.servicePaymentId = servicePaymentId;
	}
	
	
	@Override
	public String toString() {
		return "ServiceCsvDTO [cuitCuilCdi=" + cuitCuilCdi + ", amount=" + amount + ", due=" + due
				+ ", servicePaymentId=" + servicePaymentId + "]";
	}
}
