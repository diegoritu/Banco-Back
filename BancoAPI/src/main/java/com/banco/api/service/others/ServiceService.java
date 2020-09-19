package com.banco.api.service.others;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.api.dto.others.ServiceDTO;
import com.banco.api.dto.others.request.CreateServiceRequest;
import com.banco.api.exception.VendorNotFoundException;
import com.banco.api.model.ServicePayment;
import com.banco.api.model.user.Legal;
import com.banco.api.repository.ServiceRepository;
import com.banco.api.service.account.CheckingService;
import com.banco.api.service.account.SavingsService;
import com.banco.api.service.user.LegalUserService;
import com.banco.api.service.user.PhysicalUserService;

@Service
public class ServiceService {
	@Autowired
	ServiceRepository serviceRepository;
	@Autowired
	private LegalUserService legalUserService;
	@Autowired
	private PhysicalUserService physicalUserService;
	@Autowired
	private CheckingService checkingService;
	@Autowired
	private SavingsService savingsService;
	
	public Map<String, String> createService(CreateServiceRequest request) {
		ServicePayment result = null;
		Map<String, String> ids = new HashMap<String, String>();
		Collection<ServicePayment> services = new ArrayList<ServicePayment>();
		for(int i=0; i<request.getAmountOfIds();i++) {
			do {
				result = new ServicePayment(request.getName(), request.getAmount());
			}
			while(existsByServicePaymentId(result.getServicePaymentId()));
			Legal vendor = legalUserService.findByActiveUsername(request.getVendorUsername());
			if(vendor != null) {
				if(vendor.getVendorId() == null) {
					vendor.setVendorId();
				}
				if(request.getVendorAccountType() == 0) {
					result.setVendorChecking(checkingService.findByAccountNumber(request.getVendorAccountNumber()));
				}
				else if(request.getVendorAccountType() == 1) {
					result.setVendorSavings(savingsService.findByAccountNumber(request.getVendorAccountNumber()));
				}
			}
			else {
				throw new VendorNotFoundException("No se encontró al proveedor");
			}
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_MONTH, Integer.valueOf(request.getDueDay()));
			c.add(Calendar.MONTH, 1);
			Date due = c.getTime();
			result.setDue(due);
			result.setVendor(vendor);
			services.add(result);
			ids.put(result.getServicePaymentId(),vendor.getVendorId());
		}
		for(ServicePayment sp : services) {
			serviceRepository.save(sp);
		}
		return ids;
	}
	
	public ServicePayment findServiceByServicePaymentId(String servicePaymentId){
		return serviceRepository.findByServicePaymentId(servicePaymentId);
	}
	public boolean existsByServicePaymentId(String servicePaymentId) {
		return findServiceByServicePaymentId(servicePaymentId) != null;
	}

	public ServicePayment findServiceByservicePaymentId(String idServicePayment, String vendorId) {
		return serviceRepository.findByServicePaymentId(idServicePayment);
	}
	
	public boolean existsServiceByservicePaymentIdAndVendorId(String idServicePayment, String vendorId) {
		return findServiceByservicePaymentId(idServicePayment, vendorId) != null;
	}
	
	
}
