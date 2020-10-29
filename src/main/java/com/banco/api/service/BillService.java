package com.banco.api.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.api.dto.account.AccountType;
import com.banco.api.dto.others.ServiceCsvDTO;
import com.banco.api.exception.VendorNotFoundException;
import com.banco.api.model.ServicePayment;
import com.banco.api.model.user.Legal;
import com.banco.api.repository.ServiceRepository;
import com.banco.api.service.user.LegalUserService;

@Service
public class BillService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BillService.class);

	@Autowired
	ServiceRepository serviceRepository;
	@Autowired
	private LegalUserService legalUserService;
	
	public ArrayList<String> createService(List<ServiceCsvDTO> servicesCsv, String name, String vendorUsername, String accountType) {
		Legal vendor = null;
		AccountType vendorAccountType = stringToAccountType(accountType);
		Collection<ServicePayment> services = new ArrayList<ServicePayment>();
		ArrayList<String> repeatedIds = new ArrayList<String>(); //En la posición 0 lleva el id del vendor. De la posición 1 en adelante lleva los serviceIds que ya existían y no se crearon
		
		vendor = legalUserService.findByActiveUsername(vendorUsername);
		if(vendor != null) {
			if(vendor.getVendorId() == null) {
				vendor.setVendorId();
			}
			repeatedIds.add(vendor.getVendorId());
			for(ServiceCsvDTO s : servicesCsv) {
				ServicePayment serv = new ServicePayment();
				
				if(serviceRepository.existsByServicePaymentIdAndVendorIdUser(s.getServicePaymentId(), vendor.getId())) {
					repeatedIds.add(s.getServicePaymentId());
				}
				else {
					if(AccountType.CHECKING.equals(vendorAccountType)) {
						serv.setVendorChecking(vendor.getChecking());
					}
					else if(AccountType.SAVINGS.equals(vendorAccountType)) {
						serv.setVendorSavings(vendor.getSavings());
					}
					serv.setAmount(s.getAmount());
					serv.setDue(s.getDue());
					serv.setServicePaymentId(s.getServicePaymentId());
					serv.setCuitCuilCdi(s.getCuitCuilCdi());
					serv.setName(name);
					serv.setVendor(vendor);
					services.add(serv);
				}
			}
			for(ServicePayment sp : services) {
				serviceRepository.save(sp);
			}
			return repeatedIds;
			
		}
		else {
			LOGGER.warn("Vendor not found");
			throw new VendorNotFoundException("No se encontró al proveedor");
		}
		
		
		
		
		
		/*validateRequest(request);
		ServiceCreatedDTO answer = new ServiceCreatedDTO();
		AccountType vendorAccountType = stringToAccountType(request.getVendorAccountType());
		ServicePayment result = null;
		Collection<String> ids = new ArrayList<String>();
		Collection<ServicePayment> services = new ArrayList<ServicePayment>();
		Legal vendor = null;
		for(int i=0; i<request.getAmountOfIds();i++) {
			do {
				result = new ServicePayment(request.getName(), request.getAmount());
			}
			while(existsByServicePaymentId(result.getServicePaymentId()));
			vendor = legalUserService.findByActiveUsername(request.getVendorUsername());
			if(vendor != null) {
				if(vendor.getVendorId() == null) {
					vendor.setVendorId();
				}
				if(AccountType.CHECKING.equals(vendorAccountType)) {
					result.setVendorChecking(checkingService.findByAccountNumber(request.getVendorAccountNumber()));
				}
				else if(AccountType.SAVINGS.equals(vendorAccountType)) {
					result.setVendorSavings(savingsService.findByAccountNumber(request.getVendorAccountNumber()));
				}
			}
			else {
				LOGGER.warn("Vendor not found");
				throw new VendorNotFoundException("No se encontró al proveedor");
			}
			Date dueDate = DateUtils.parse(request.getDueDate());
			result.setDue(dueDate);
			result.setVendor(vendor);
			services.add(result);
			ids.add(result.getServicePaymentId());
		}
		answer.setVendorId(vendor.getVendorId());
		answer.setIds(ids);
		for(ServicePayment sp : services) {
			serviceRepository.save(sp);
		}
		return answer;
		*/
	}

	public ServicePayment searchNotPayedServiceBill(String servicePaymentId, String vendorId) {
		if (!legalUserService.vendorExists(vendorId))
			return null;

		return serviceRepository.findByServicePaymentIdAndPaid(servicePaymentId, false);
	}
	
	public ServicePayment findServiceByServicePaymentId(String servicePaymentId){
		return serviceRepository.findByServicePaymentId(servicePaymentId);
	}

	public boolean existsByServicePaymentId(String servicePaymentId) {
		return findServiceByServicePaymentId(servicePaymentId) != null;
	}

	public ServicePayment findServiceByServicePaymentId(String idServicePayment, String vendorId) {
		return serviceRepository.findByServicePaymentId(idServicePayment);
	}
	
	public boolean existsServiceByservicePaymentIdAndVendorId(String idServicePayment, String vendorId) {
		return findServiceByServicePaymentId(idServicePayment, vendorId) != null;
	}

	/*private void validateRequest(CreateServiceBillRequest request) {
		if (!DateUtils.isValid(request.getDueDate()))
			throw new InvalidServiceBillCreationRequestException("Formato inválido de fecha de vencimiento");

		if (request.getAmountOfIds() <= 0)
			throw new InvalidServiceBillCreationRequestException("La cantidad de ids debe ser mayor a cero");

		if (request.getAmount() <= 0)
			throw new InvalidServiceBillCreationRequestException("El monto a pagar debe ser mayor a cero");

		if (StringUtils.isEmpty(request.getVendorUsername()))
			throw new InvalidServiceBillCreationRequestException("Debe especificar el dueño del servicio");

		if (StringUtils.isEmpty(request.getVendorAccountNumber()))
			throw new InvalidServiceBillCreationRequestException("Debe especificar el número de cuenta de cobro del vendedor");
	}*/

	private AccountType stringToAccountType(String accountType) {
		return AccountType.valueOf(accountType.toUpperCase());
	}
	
}
