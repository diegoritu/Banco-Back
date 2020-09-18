package com.banco.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.api.dto.others.ServiceDTO;
import com.banco.api.dto.others.request.CreateServiceRequest;
import com.banco.api.service.others.ServiceService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/service")
public class ServiceController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceController.class);
	@Autowired
	ServiceService serviceService;
	
	@PostMapping("/create")
    public ResponseEntity createService(@RequestBody CreateServiceRequest request){
		ServiceDTO result = serviceService.createService(request);
		LOGGER.info("Create service operation started. {}", request.toString());

		if(result == null) {
			LOGGER.warn("Name or idServicePayment repeated");
			return ResponseEntity
                    .status(HttpStatus.IM_USED)
                    .body("{\"error\": \" Name or idServicePayment repeated \"}");    		
		}
		else if(result.getVendor() == null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		else {
    		return new ResponseEntity<ServiceDTO>(result, HttpStatus.CREATED);
		}
	}
}
