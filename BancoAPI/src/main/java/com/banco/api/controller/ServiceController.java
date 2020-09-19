package com.banco.api.controller;

import java.util.Collection;
import java.util.Map;

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
import com.banco.api.exception.VendorNotFoundException;
import com.banco.api.service.others.ServiceService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/service")
public class ServiceController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceController.class);
	@Autowired
	ServiceService serviceService;
	
	@PostMapping("/create")
    public ResponseEntity<Map<String, String>> createService(@RequestBody CreateServiceRequest request){
		try {
		Map<String, String> ids = serviceService.createService(request);
		LOGGER.info("Create services operation started. {}", request.toString());
		return new ResponseEntity<Map<String, String>>(ids, HttpStatus.CREATED);
		}
		catch(VendorNotFoundException ex){
			LOGGER.warn(ex.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	
}
