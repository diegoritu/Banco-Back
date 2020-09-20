package com.banco.api.dto.others;

import com.banco.api.dto.account.CheckingDTO;
import com.banco.api.dto.account.SavingsDTO;
import com.banco.api.dto.user.LegalUserDTO;
import com.banco.api.dto.user.PhysicalUserDTO;

public class ServiceDTO {
	private String name;
	private float amount;
	private String servicePaymentId;
	private boolean paid;
	private LegalUserDTO legalWhoPays;
	private PhysicalUserDTO physicalWhoPays;
	private LegalUserDTO vendor;
	private String dueDate;
	private CheckingDTO vendorChecking;
	private SavingsDTO vendorSavings;
	
	public ServiceDTO(String name, float amount, String idServicePayment, boolean paid,
					  LegalUserDTO legalWhoPays, PhysicalUserDTO physicalWhoPays, LegalUserDTO vendor, String dueDate, CheckingDTO vendorChecking, SavingsDTO vendorSavings) {
		super();
		this.name = name;
		this.amount = amount;
		this.servicePaymentId = idServicePayment;
		this.paid = paid;
		this.legalWhoPays = legalWhoPays;
		this.physicalWhoPays = physicalWhoPays;
		this.vendor = vendor;
		this.dueDate = dueDate;
		this.vendorChecking = vendorChecking;
		this.vendorSavings = vendorSavings;
	}
	
	public ServiceDTO() {}
	
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
	public String getServicePaymentId() {
		return servicePaymentId;
	}
	public void setServicePaymentId(String idServicePayment) {
		this.servicePaymentId = idServicePayment;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public LegalUserDTO getLegalWhoPays() {
		return legalWhoPays;
	}
	public void setLegalWhoPays(LegalUserDTO legalWhoPays) {
		this.legalWhoPays = legalWhoPays;
	}
	public PhysicalUserDTO getPhysicalWhoPays() {
		return physicalWhoPays;
	}
	public void setPhysicalWhoPays(PhysicalUserDTO physicalWhoPays) {
		this.physicalWhoPays = physicalWhoPays;
	}
	public LegalUserDTO getVendor() {
		return vendor;
	}
	public void setVendor(LegalUserDTO vendor) {
		this.vendor = vendor;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public CheckingDTO getVendorChecking() {
		return vendorChecking;
	}
	public void setVendorChecking(CheckingDTO vendorChecking) {
		this.vendorChecking = vendorChecking;
	}
	public SavingsDTO getVendorSavings() {
		return vendorSavings;
	}
	public void setVendorSavings(SavingsDTO vendorSavings) {
		this.vendorSavings = vendorSavings;
	}

	@Override
	public String toString() {
		return "ServiceDTO [name=" + name + ", amount=" + amount + ", servicePaymentId=" + servicePaymentId + ", paid="
				+ paid + ", legalWhoPays=" + legalWhoPays + ", physicalWhoPays=" + physicalWhoPays + ", vendor="
				+ vendor + ", dueDate=" + dueDate + ", vendorChecking=" + vendorChecking + ", vendorSavings=" + vendorSavings
				+ "]";
	}

}
