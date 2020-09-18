package com.banco.api.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.banco.api.dto.others.ServiceDTO;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.user.Legal;
import com.banco.api.model.user.Physical;


@Entity
@Table(name="services")
public class ServicePayment {
    private String name;
    private float amount;
    private String idServicePayment;
    private boolean paid;
    private boolean regular;
    
    @ManyToOne
    private Checking vendorChecking;
    
    @ManyToOne
    private Savings vendorSavings;
    
    @ManyToOne
    private Legal legalWhoPays;
    @ManyToOne
    private Physical physicalWhoPays;
    
    @ManyToOne
    private Legal vendor;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date due;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idService;

    public ServicePayment() {}
    
    public ServicePayment(String name, float amount, String idServicePayment, boolean regular) {
        super();
        this.name = name;
        this.amount = amount;
        this.idServicePayment = idServicePayment;
        this.paid = false;
        this.regular = regular;
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
	public Legal getLegalWhoPays() {
		return legalWhoPays;
	}
	public void setLegalWhoPays(Legal legalWhoPays) {
		this.legalWhoPays = legalWhoPays;
	}
	public Physical getPhysicalWhoPays() {
		return physicalWhoPays;
	}
	public void setPhysicalWhoPays(Physical physicalWhoPays) {
		this.physicalWhoPays = physicalWhoPays;
	}
	public Legal getVendor() {
		return vendor;
	}
	public void setVendor(Legal vendor) {
		this.vendor = vendor;
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

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }
    

    public Checking getVendorChecking() {
		return vendorChecking;
	}

	public void setVendorChecking(Checking vendorChecking) {
		this.vendorChecking = vendorChecking;
	}

	public Savings getVendorSavings() {
		return vendorSavings;
	}

	public void setVendorSavings(Savings vendorSavings) {
		this.vendorSavings = vendorSavings;
	}


	@Override
	public String toString() {
		return "ServicePayment [name=" + name + ", amount=" + amount + ", idServicePayment=" + idServicePayment
				+ ", paid=" + paid + ", regular=" + regular + ", vendorChecking=" + vendorChecking + ", vendorSavings="
				+ vendorSavings + ", legalWhoPays=" + legalWhoPays + ", physicalWhoPays=" + physicalWhoPays
				+ ", vendor=" + vendor + ", due=" + due + ", idService=" + idService + "]";
	}

	public ServiceDTO toView() {
		ServiceDTO result = new ServiceDTO();
		result.setAmount(this.getAmount());
		result.setDue(this.getDue().toString());
		result.setIdServicePayment(this.getIdServicePayment());
		result.setName(this.getName());
		if(this.getLegalWhoPays() != null) {
			result.setLegalWhoPays(this.getLegalWhoPays().toView());
		}
		else {
			result.setLegalWhoPays(null);
		}
		if(this.getPhysicalWhoPays() != null) {
			result.setPhysicalWhoPays(this.getPhysicalWhoPays().toView());
		}
		else {
			result.setPhysicalWhoPays(null);
		}
		if(this.getVendorChecking() != null) {
			result.setVendorChecking(this.getVendorChecking().toView());
		}
		else {
			result.setVendorChecking(null);
		}
		if(this.getVendorSavings() != null) {
			result.setVendorSavings(vendorSavings.toView());
		}
		else {
			result.setVendorSavings(null);
		}
		result.setPaid(this.isPaid());
		result.setRegular(this.isRegular());
		if(this.getVendor() != null) {
			result.setVendor(this.getVendor().toView());
		}
		else {
			result.setVendor(null);
		}
		return result;
	}
}
