package com.banco.api.model.user;

import com.banco.api.adapter.Externalizable;
import com.banco.api.dto.user.LegalUserDTO;
import com.banco.api.dto.user.UserType;
import com.banco.api.model.DebitCard;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("Legal")
public class Legal extends User implements Externalizable<LegalUserDTO> {

    private String businessName;
    private String vendorId;
    
    @OneToOne
    @JoinColumn(name = "savingsAccountId")
    private Savings savings; //Caja de Ahorro

    @OneToOne
    @JoinColumn(name = "checkingAccountId")
    private Checking checking; //Cuenta Corriente

    
    public Legal() {
    	super();
        this.userTypeNumber = UserType.LEGAL.getValue();
    }

    public Legal(int id, String cuitCuilCdi, String usr, String address, String phone,
                 boolean active, String businessName, Savings savings, Checking checking) {
        super(id, cuitCuilCdi, usr, address, phone, active);
        this.businessName = businessName;
        this.savings = savings;
        this.checking = checking;
        this.userTypeNumber = UserType.LEGAL.getValue();
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Savings getSavings() {
        return savings;
    }

    public void setSavings(Savings savings) {
        this.savings = savings;
    }

    public Checking getChecking() {
        return checking;
    }

	public void setChecking(Checking checking) {
        this.checking = checking;
    }

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId() {
		final int BASE_LENGHT = 5;
		String bName = this.getBusinessName().replace(" ","");
    	String numbers = "123456789";
    	List<String> letters = Arrays.asList(numbers.split(""));
    	Collections.shuffle(letters);
    	String vendorId = letters.stream().collect(Collectors.joining()).substring(0, BASE_LENGHT);
    	vendorId = bName + vendorId;
		this.vendorId = vendorId;
	}

	@Override
    public String toString() {
        return "Legal{" +
                "businessName='" + businessName + '\'' +
                ", savings=" + savings +
                ", checking=" + checking +
                '}';
    }

    @Override
    public LegalUserDTO toView() {
        LegalUserDTO view = new LegalUserDTO();
        view.setId(this.getId());
        view.setActive(this.isActive());
        view.setAddress(this.getAddress());
        view.setCuitCuilCdi(this.getCuitCuilCdi());
        view.setPhone(this.getPhone());
        view.setUsername(this.getUsername());
        view.setUserType(UserType.valueOf(this.getUserType()).toString());
        view.setFirstLogin(this.isFirstLogin());
        
        if(this.getSavings() == null || !this.getSavings().isActive()) {
        	view.setSavings(null);
        }
        else {
            view.setSavings(this.getSavings().toView());       	
        }
        
        if(this.getChecking() == null || !this.getChecking().isActive()) {
            view.setChecking(null);
        }
        else {
            view.setChecking(this.getChecking().toView());
        }

        view.setBusinessName(this.getBusinessName());
        return view;
    }
}
