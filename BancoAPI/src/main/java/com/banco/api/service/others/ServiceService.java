package com.banco.api.service.others;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.api.dto.others.ServiceDTO;
import com.banco.api.dto.others.request.CreateServiceRequest;
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
	
	public ServiceDTO createService(CreateServiceRequest request) {
		ServicePayment result = null;
		if(!existsByIdServicePaymentOrName(request.getIdServicePayment(), request.getName())) {
			result = new ServicePayment(request.getName(), request.getAmount(), request.getIdServicePayment(), request.isRegular());
			Legal vendor = legalUserService.findByActiveUsername(request.getVendorUsername());
			if(vendor != null) {
				if(request.getVendorAccountType() == 0) {
					result.setVendorChecking(checkingService.findByAccountNumber(request.getVendorAccountNumber()));
				}
				else if(request.getVendorAccountType() == 1) {
					result.setVendorSavings(savingsService.findByAccountNumber(request.getVendorAccountNumber()));
				}
			}
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_MONTH, Integer.valueOf(request.getDueDay()));
			c.add(Calendar.MONTH, 1);
			Date due = c.getTime();
			result.setDue(due);
			result.setVendor(vendor);
			serviceRepository.save(result);
			return result.toView();
		}
		return null;
	}
	
	public ServicePayment findServiceByIdServicePaymentOrName(String idServicePayment, String name){
		return serviceRepository.findByIdServicePaymentOrName(idServicePayment, name);
	}
	public boolean existsByIdServicePaymentOrName(String idServicePayment, String name) {
		return findServiceByIdServicePaymentOrName(idServicePayment, name) != null;
	}
	
	
}
