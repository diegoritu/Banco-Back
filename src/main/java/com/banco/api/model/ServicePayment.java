package com.banco.api.model;

import com.banco.api.adapter.Externalizable;
import com.banco.api.dto.others.ServiceDTO;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.user.Legal;
import com.banco.api.model.user.Physical;
import com.banco.api.utils.DateUtils;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="services")
public class ServicePayment implements Externalizable<ServiceDTO> {
    private String name;
    private float amount;
    private String servicePaymentId;
    private boolean paid;
    private String cuitCuilCdi;
    private boolean automatic;
    
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
    
    public String getServicePaymentId() {
		return servicePaymentId;
	}

	public void setServicePaymentId(String id) {
		/*final int BASE_LENGHT = 9;
    	String numbers = "123456789";
    	List<String> code = Arrays.asList(numbers.split(""));
    	Collections.shuffle(code);
    	String id = code.stream().collect(Collectors.joining()).substring(0, BASE_LENGHT);
		*/
    	this.servicePaymentId = id;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
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

	public String getCuitCuilCdi() {
		return cuitCuilCdi;
	}

	public void setCuitCuilCdi(String cuitCuilCdi) {
		this.cuitCuilCdi = cuitCuilCdi;
	}

	public boolean isAutomatic() {
		return automatic;
	}

	public void setAutomatic(boolean automatic) {
		this.automatic = automatic;
	}

	@Override
	public String toString() {
		return "ServicePayment{" +
				"name='" + name + '\'' +
				", amount=" + amount +
				", servicePaymentId='" + servicePaymentId + '\'' +
				", paid=" + paid +
				", cuitCuilCdi='" + cuitCuilCdi + '\'' +
				", automatic=" + automatic +
				", vendorChecking=" + vendorChecking +
				", vendorSavings=" + vendorSavings +
				", legalWhoPays=" + legalWhoPays +
				", physicalWhoPays=" + physicalWhoPays +
				", vendor=" + vendor +
				", due=" + due +
				", idService=" + idService +
				'}';
	}

	@Override
	public ServiceDTO toView() {
		ServiceDTO result = new ServiceDTO();
		result.setAmount(this.getAmount());
		result.setDueDate(DateUtils.format(this.due));
		result.setServicePaymentId(this.getServicePaymentId());
		result.setName(this.getName());
		result.setCuitCuilCdi(this.getCuitCuilCdi());
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
		if(this.getVendor() != null) {
			result.setVendor(this.getVendor().toView());
		}
		else {
			result.setVendor(null);
		}
		return result;
	}
}
