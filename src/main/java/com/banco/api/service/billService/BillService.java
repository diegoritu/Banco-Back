package com.banco.api.service.billService;

import com.banco.api.dto.account.AccountType;
import com.banco.api.dto.movement.MovementType;
import com.banco.api.dto.others.ServiceCsvDTO;
import com.banco.api.exception.BusinessCBUNotFoundException;
import com.banco.api.exception.InsufficientBalanceException;
import com.banco.api.exception.VendorNotFoundException;
import com.banco.api.model.ServicePayment;
import com.banco.api.model.scheduledTransaction.ScheduledTransactionStatus;
import com.banco.api.model.scheduledTransaction.billService.ScheduledCollectService;
import com.banco.api.model.user.Legal;
import com.banco.api.model.user.Physical;
import com.banco.api.published.request.collectService.CollectServiceRequest;
import com.banco.api.repository.ServiceRepository;
import com.banco.api.repository.scheduledTransaction.ScheduledCollectServiceRepository;
import com.banco.api.service.MovementService;
import com.banco.api.service.user.LegalUserService;
import com.banco.api.service.user.PhysicalUserService;
import com.banco.api.utils.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.String.format;

@Service
public class BillService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BillService.class);

	@Autowired
	private ServiceRepository serviceRepository;
	@Autowired
	private ScheduledCollectServiceRepository scheduledCollectServiceRepository;
	@Autowired
	private LegalUserService legalUserService;
	@Autowired
	private PhysicalUserService physicalUserService;
	@Autowired
	private MovementService movementService;


	public CreateBillServiceResult createService(List<ServiceCsvDTO> servicesCsv, String name, String vendorUsername, String accountType) {
		AccountType vendorAccountType = stringToAccountType(accountType);
		Collection<ServicePayment> services = new ArrayList<>();
		ArrayList<String> repeatedIds = new ArrayList<>(); //Lleva los serviceIds que ya existían y por ello no se crearon

		Legal vendor = legalUserService.findByActiveUsername(vendorUsername);
		if (vendor == null) {
			LOGGER.warn("Vendor not found");
			throw new VendorNotFoundException("No se encontró al proveedor");
		} else {
			if(vendor.getVendorId() == null) {
				vendor.setVendorId();
			}
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
			return new CreateBillServiceResult(vendor.getVendorId(), repeatedIds);
		}
	}

	public void createPublishedBillServices(CollectServiceRequest collectServiceRequest) {
		String serviceProviderCBU = collectServiceRequest.getServiceProviderCBU();

		if (!legalUserService.existsByCBU(serviceProviderCBU) && !physicalUserService.existsByCBU(serviceProviderCBU)) {
			throw new BusinessCBUNotFoundException("La cuenta del proveedor no existe");
		}

		if (!legalUserService.existsByBusinessName(collectServiceRequest.getServiceProviderName())) {
			throw new VendorNotFoundException("El nombre de proveedor no existe");
		}

		if (CollectionUtils.isEmpty(collectServiceRequest.getCollectServices())) {
			throw new IllegalArgumentException("El listado de servicios está vacío");
		}

		collectServiceRequest.getCollectServices().forEach(collectService -> {
			//TODO: validacion de existe clientcuit, existe clientcbu (solo cuando no es null), duplicatedId+date, validar formato de dueDate. Validar monto mayor a cero y campos obligatorios

			Legal serviceProvider = legalUserService.findByBusinessName(collectServiceRequest.getServiceProviderName());

			ServicePayment servicePayment = new ServicePayment();
			servicePayment.setName(serviceProvider.getBusinessName());
			servicePayment.setAmount(collectService.getAmount());
			servicePayment.setServicePaymentId(collectService.getServiceId());
			servicePayment.setPaid(false);
			servicePayment.setCuitCuilCdi(collectService.getClientCUIT());
			servicePayment.setVendor(serviceProvider);
			servicePayment.setDue(DateUtils.parse(collectService.getDueDate()));

			if (serviceProvider.getSavings().getCbu().equals(serviceProviderCBU)) {
				servicePayment.setVendorSavings(serviceProvider.getSavings());
			} else if (serviceProvider.getChecking().getCbu().equals(serviceProviderCBU)) {
				servicePayment.setVendorChecking(serviceProvider.getChecking());
			}

			ServicePayment saveResult = serviceRepository.save(servicePayment);

			if (collectService.getClientCBU() != null) {
				ScheduledCollectService scheduledCollectService = new ScheduledCollectService(saveResult,
						serviceProviderCBU, collectService.getClientCBU());
				scheduledCollectServiceRepository.save(scheduledCollectService);
			}
		});
	}

	public void collectService(ScheduledCollectService scheduledCollectService) {
		LOGGER.info("Collecting service: {}", scheduledCollectService.toString());
		ServicePayment servicePayment = scheduledCollectService.getServicePayment();
		String clientCbu = scheduledCollectService.getClientCbu();
		try {
			Physical physicalWhoPays = physicalUserService.findByCBU(clientCbu);
			if (physicalWhoPays != null) {
				servicePayment.setPhysicalWhoPays(physicalWhoPays);
			} else {
				Legal legalWhoPays = legalUserService.findByCBU(clientCbu);
				servicePayment.setLegalWhoPays(legalWhoPays);
			}
			movementService.transferBetweenTwoAccountsByCBU(clientCbu, scheduledCollectService.getServiceProviderCBU(),
					servicePayment.getAmount(), MovementType.SERVICES_PAYMENT, MovementType.SERVICES_PAYMENT);
			servicePayment.setPaid(true);
			scheduledCollectService.setStatus(ScheduledTransactionStatus.DONE);

		} catch (InsufficientBalanceException ex) {
			LOGGER.warn(ex.getLocalizedMessage());
			scheduledCollectService.setStatus(ScheduledTransactionStatus.ERROR);
			scheduledCollectService.setFailureCode("CLIENT_ACCOUNT_INSUFFICIENT_FUNDS");
			scheduledCollectService.setFailureMessage(format("La cuenta CBU %s del cliente no posee fondos suficientes", clientCbu));

		} finally {
			scheduledCollectServiceRepository.save(scheduledCollectService);
			if (ScheduledTransactionStatus.ERROR.equals(scheduledCollectService.getStatus())) {
				LOGGER.warn("Could not collect service. Id: {}, ServicePaymentId: {}", scheduledCollectService.getId(),
						servicePayment.getServicePaymentId());
			} else {
				LOGGER.info("Successfully collected service. Id: {}, ServicePaymentId: {}", scheduledCollectService.getId(),
						servicePayment.getServicePaymentId());
			}
		}
	}

	public ServicePayment searchNotPayedServiceBill(String servicePaymentId, String vendorId) {
		if (!legalUserService.existsByVendorId(vendorId))
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
