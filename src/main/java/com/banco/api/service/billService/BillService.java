package com.banco.api.service.billService;

import com.banco.api.dto.account.AccountType;
import com.banco.api.dto.others.ServiceCsvDTO;
import com.banco.api.exception.*;
import com.banco.api.model.ServicePayment;
import com.banco.api.model.scheduledTransaction.ScheduledTransactionStatus;
import com.banco.api.model.scheduledTransaction.billService.ScheduledCollectService;
import com.banco.api.model.user.Legal;
import com.banco.api.model.user.Physical;
import com.banco.api.published.request.collectService.CollectServiceRequest;
import com.banco.api.published.response.collectService.CollectServiceResponse;
import com.banco.api.published.response.collectService.list.CollectServiceError;
import com.banco.api.published.response.collectService.list.CollectServiceItem;
import com.banco.api.published.response.collectService.list.CollectServiceResource;
import com.banco.api.published.response.collectService.list.CollectServiceStatus;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isEmpty;

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

				if(duplicatedServicePaymentId(s.getServicePaymentId(), s.getDue(), vendor.getVendorId())) {
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

	public CollectServiceResponse createPublishedBillServices(CollectServiceRequest collectServiceRequest) {
		validateCollectServiceRequest(collectServiceRequest);

		String serviceProviderCBU = collectServiceRequest.getServiceProviderCBU();
		Legal serviceProvider = legalUserService.findByCBU(serviceProviderCBU);
		if (serviceProvider.getVendorId() == null) {
			serviceProvider.setVendorId();
		}
		collectServiceRequest.getServices().forEach(collectService -> {

			ServicePayment servicePayment = new ServicePayment();
			servicePayment.setName(collectService.getName());
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

			boolean automatic = collectService.getClientCBU() != null;
			servicePayment.setAutomatic(automatic);
			ServicePayment saveResult = serviceRepository.save(servicePayment);

			if (automatic) {
				ScheduledCollectService scheduledCollectService = new ScheduledCollectService(saveResult,
						serviceProviderCBU, collectService.getClientCBU());
				scheduledCollectServiceRepository.save(scheduledCollectService);
			}
		});
		return new CollectServiceResponse(serviceProvider.getVendorId());
	}

	private void validateCollectServiceRequest(CollectServiceRequest collectServiceRequest) {
		String serviceProviderCBU = collectServiceRequest.getServiceProviderCBU();
		if (!legalUserService.existsByCBU(serviceProviderCBU)) {
			throw new BusinessCBUNotFoundException("La cuenta del proveedor no existe");
		}

		if (CollectionUtils.isEmpty(collectServiceRequest.getServices())) {
			throw new IllegalArgumentException("El listado de servicios no debe estar vacío");
		}

        Legal serviceProviderUser = legalUserService.findByCBU(serviceProviderCBU);
        collectServiceRequest.getServices().forEach(s -> {
			if (s.getAmount() == null || s.getAmount() <= 0)
				throw new IllegalArgumentException("El monto debe ser mayor a cero");

			if (isEmpty(s.getServiceId()))
				throw new IllegalArgumentException("serviceId no debe estar vacío");

			if (!DateUtils.isValid(s.getDueDate()))
				throw new InvalidDateFormatException(format("Fecha %s inválida", s.getDueDate()));

			if (duplicatedServicePaymentId(s.getServiceId(), DateUtils.parse(s.getDueDate()), serviceProviderUser.getVendorId()))
				throw new DuplicatedServiceIdException(format("El servicio ID %s con fecha de vencimiento %s ya existe",
						s.getServiceId(), s.getDueDate()));

			if (isEmpty(s.getName()))
				throw new IllegalArgumentException("name no debe estar vacío");

			if (!physicalUserService.existsActiveByCuitCuilCdi(s.getClientCUIT()) && !legalUserService.existsActiveByCuitCuilCdi(s.getClientCUIT()))
				throw new ClientNotFoundException(format("El cliente %s no existe", s.getClientCUIT()));

			if (s.getClientCBU() != null && !physicalUserService.existsByCBU(s.getClientCBU())  && !legalUserService.existsByCBU(s.getClientCUIT()))
				throw new ClientCBUNotFoundException(format("La cuenta de cliente CBU %s no existe", s.getClientCBU()));
		});

		collectServiceRequest.getServices().forEach(s1 ->
				collectServiceRequest.getServices().forEach(s2 -> {
					if (s1 != s2 && s1.getServiceId().equals(s2.getServiceId()) && s1.getDueDate().equals(s2.getDueDate())) {
						String message = format("Servicio ID %s con fecha de vencimiento %s duplicado en request", s1.getServiceId(),
								s1.getDueDate());
						throw new IllegalArgumentException(message);
					}
				})
		);
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
			movementService.collectScheduledService(clientCbu, scheduledCollectService.getServiceProviderCBU(),
					servicePayment);
			servicePayment.setPaid(true);
            serviceRepository.save(servicePayment);
            scheduledCollectService.setStatus(ScheduledTransactionStatus.DONE);

		} catch (InsufficientBalanceException ex) {
			LOGGER.warn(ex.getLocalizedMessage());
			scheduledCollectService.setStatus(ScheduledTransactionStatus.ERROR);
			scheduledCollectService.setFailureCode("CLIENT_INSUFFICIENT_FUNDS");
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

	public List<CollectServiceItem> getCollectServices(String serviceProviderId, String fromDateParam) {
		if (!legalUserService.existsByVendorId(serviceProviderId))
			throw new VendorNotFoundException("Proveedor no encontrado");

		if (fromDateParam != null && !DateUtils.isValid(fromDateParam))
			throw new InvalidDateFormatException(format("Fecha %s inválida", fromDateParam));

		Date fromDueDate = DateUtils.parse(fromDateParam);

		List<ScheduledCollectService> scheduledCollectServices = scheduledCollectServiceRepository
				.findAllByVendorIdFromScheduledDate(serviceProviderId, fromDueDate);

		Stream<CollectServiceItem> automaticServiceItems = scheduledCollectServices.stream()
				.map(s -> {
					String serviceId = s.getServicePayment().getServicePaymentId();
					String dueDate = DateUtils.format(s.getScheduledDate());
					boolean automatic = s.getServicePayment().isAutomatic();
					CollectServiceResource resource = new CollectServiceResource(serviceId, dueDate, automatic);

					CollectServiceError error = ScheduledTransactionStatus.ERROR.equals(s.getStatus()) ?
							new CollectServiceError(s.getFailureCode(), s.getFailureMessage()) : null;

					String status;
					switch (s.getStatus()) {
						case PENDING:
							status = CollectServiceStatus.PENDING;
							break;
						case DONE:
							status = CollectServiceStatus.PAID;
							break;
						case ERROR:
						default:
							status = CollectServiceStatus.ERROR;
							break;
					}
					return new CollectServiceItem(resource, status, error);
				});

		List<ServicePayment> manualServicePayments = serviceRepository.findManualByVendorIdFromDueDate(serviceProviderId, fromDueDate);
		Stream<CollectServiceItem> manualServiceItems = manualServicePayments.stream()
				.map(s -> {
					String serviceId = s.getServicePaymentId();
					String dueDate = DateUtils.format(s.getDue());
					CollectServiceResource resource = new CollectServiceResource(serviceId, dueDate, s.isAutomatic());
					String status = s.isPaid() ? CollectServiceStatus.PAID : CollectServiceStatus.PENDING;
					return new CollectServiceItem(resource, status, null);
				});

		return Stream.concat(automaticServiceItems, manualServiceItems)
				.collect(toList());
	}

	public ServicePayment searchNotPayedServiceBill(String servicePaymentId, String vendorId, String dueDate) {
		Date dueDateD = null; 
			try {
				dueDateD = new SimpleDateFormat("yyyy-MM-dd").parse(dueDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		if (!legalUserService.existsByVendorId(vendorId))
		{	
			return null;
		}
		else if (dueDateD != null) {
			Legal legalUser = legalUserService.findByVendorId(vendorId);
			return serviceRepository.findByVendorIdUserAndServicePaymentIdAndPaidAndDue(legalUser.getId(), servicePaymentId, false, dueDateD);	
		}
		else {
			return null;
		}
		
	}

	public ServicePayment findServiceByServicePaymentId(String idServicePayment, int vendorIdUser) {
		return serviceRepository.findByVendorIdUserAndServicePaymentIdAndPaid(vendorIdUser, idServicePayment, false);
	}

	private boolean duplicatedServicePaymentId(String servicePaymentId, Date dueDate, String vendorId) {
	    if (vendorId == null) return false;

		return serviceRepository.existsByServicePaymentIdAndDueAndVendor_VendorId(servicePaymentId, dueDate, vendorId);
	}

	private AccountType stringToAccountType(String accountType) {
		return AccountType.valueOf(accountType.toUpperCase());
	}

}
