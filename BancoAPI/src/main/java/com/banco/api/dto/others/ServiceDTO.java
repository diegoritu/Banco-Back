package com.banco.api.dto.others;

import com.banco.api.dto.account.CheckingDTO;
import com.banco.api.dto.account.SavingsDTO;
import com.banco.api.dto.user.LegalUserDTO;
import com.banco.api.dto.user.PhysicalUserDTO;

public class ServiceDTO {
	private String name;
	private float amount;
	private String idServicePayment;
	private boolean paid;
	private boolean regular;
	private LegalUserDTO legalWhoPays;
	private PhysicalUserDTO physicalWhoPays;
	private LegalUserDTO vendor;
	private String due;
	private CheckingDTO vendorChecking;
	private SavingsDTO vendorSavings;
	
	public ServiceDTO(String name, float amount, String idServicePayment, boolean paid, boolean regular,
			LegalUserDTO legalWhoPays, PhysicalUserDTO physicalWhoPays, LegalUserDTO vendor, String due, CheckingDTO vendorChecking, SavingsDTO vendorSavings) {
		super();
		this.name = name;
		this.amount = amount;
		this.idServicePayment = idServicePayment;
		this.paid = paid;
		this.regular = regular;
		this.legalWhoPays = legalWhoPays;
		this.physicalWhoPays = physicalWhoPays;
		this.vendor = vendor;
		this.due = due;
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
	public String getIdServicePayment() {
		return idServicePayment;
	}
	public void setIdServicePayment(String idServicePayment) {
		this.idServicePayment = idServicePayment;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public boolean isRegular() {
		return regular;
	}
	public void setRegular(boolean regular) {
		this.regular = regular;
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
	public String getDue() {
		return due;
	}
	public void setDue(String due) {
		this.due = due;
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
		return "ServiceDTO [name=" + name + ", amount=" + amount + ", idServicePayment=" + idServicePayment + ", paid="
				+ paid + ", regular=" + regular + ", legalWhoPays=" + legalWhoPays + ", physicalWhoPays="
				+ physicalWhoPays + ", vendor=" + vendor + ", due=" + due + ", vendorChecking=" + vendorChecking
				+ ", vendorSavings=" + vendorSavings + "]";
	}
}
