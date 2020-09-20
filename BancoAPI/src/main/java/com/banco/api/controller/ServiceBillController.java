package com.banco.api.controller;

import com.banco.api.dto.others.ServiceDTO;
import com.banco.api.dto.others.request.CreateServiceBillRequest;
import com.banco.api.exception.InvalidServiceBillCreationRequestException;
import com.banco.api.exception.VendorNotFoundException;
import com.banco.api.model.ServicePayment;
import com.banco.api.service.others.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/service-bill")
public class ServiceBillController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBillController.class);

	@Autowired
    BillService billService;
	
	@PostMapping("/create")
    public ResponseEntity createServiceBills(@RequestBody CreateServiceBillRequest request){
		try {
		Map<String, String> ids = billService.createService(request);
		LOGGER.info("Create service bills operation started. {}", request.toString());
		return new ResponseEntity<Map<String, String>>(ids, HttpStatus.CREATED);
		}
		catch(VendorNotFoundException ex){
			LOGGER.warn(ex.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (InvalidServiceBillCreationRequestException ex ) {
		    LOGGER.warn(ex.getLocalizedMessage());
		    return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + ex.getLocalizedMessage() + "\"}");
        }
	}

	@GetMapping("/search")
    public ResponseEntity<ServiceDTO> getServiceBill(@RequestParam String servicePaymentId, @RequestParam String vendorId) {
        ServicePayment servicePayment = billService.searchNotPayedServiceBill(servicePaymentId, vendorId);
        if (servicePayment != null) {
            return new ResponseEntity<>(servicePayment.toView(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
	
}
