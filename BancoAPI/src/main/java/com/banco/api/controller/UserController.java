package com.banco.api.controller;

import com.banco.api.dto.user.request.LegalUserRequest;
import com.banco.api.dto.user.request.PhysicalUserRequest;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.user.Legal;
import com.banco.api.model.user.Physical;
import com.banco.api.dto.user.LegalUserDTO;
import com.banco.api.dto.user.PhysicalUserDTO;
import com.banco.api.service.account.CheckingService;
import com.banco.api.service.account.SavingsService;
import com.banco.api.service.user.LegalUserService;
import com.banco.api.service.user.PhysicalUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private PhysicalUserService physicalUserService;
    @Autowired
    private LegalUserService legalUserService;
    @Autowired
    private SavingsService savingsService;
    @Autowired
    private CheckingService checkingService;

    @PutMapping("/physical")
    public ResponseEntity<PhysicalUserDTO> createPhysical(@RequestBody PhysicalUserRequest request) {
        
    	if(physicalUserService.existsUser(request.getUsername()) == false)
    	{
	    	LOGGER.info("Creating physical user {}", request.toString());
	        
	    	Savings savingsAccount = savingsService.createAccount();
	    	Checking checkingAccount = null;
	    	
	    	if(request.isWithCheckingAccount())
	    	{
	    		checkingAccount = checkingService.createAccount();
	    	}
	    	
	    	return new ResponseEntity<>(physicalUserService.createUser(request, savingsAccount, checkingAccount), HttpStatus.CREATED);
    	}
    	else 
    	{
	        return new ResponseEntity<>(HttpStatus.IM_USED);
   		
    	}
    }
    @PutMapping("/legal")
    public ResponseEntity<LegalUserDTO> createLegal(@RequestBody LegalUserRequest request) {
        
    	if(legalUserService.existsUser(request.getUsername()) == false)
    	{
        	LOGGER.info("Creating legal user {}", request.toString());
        	
	    	Savings savingsAccount = savingsService.createAccount();
	    	Checking checkingAccount = null;
	    	
	    	if(request.isWithCheckingAccount())
	    	{
	    		checkingAccount = checkingService.createAccount();
	    	}

	    	
            return new ResponseEntity<>(legalUserService.createUser(request, savingsAccount, checkingAccount), HttpStatus.CREATED);
    	}
    	else 
    	{
	        return new ResponseEntity<>(HttpStatus.IM_USED);
   		
    	}

    }

    @GetMapping("/getLegalUser")
    public ResponseEntity<LegalUserDTO> getLegalUser(@RequestParam String username) {
        
    	
    	if(legalUserService.existsUser(username) )
    	{
    		Legal user = legalUserService.findByUsername(username);
    			    	
            return new ResponseEntity<>(user.toView(), HttpStatus.OK);
    	}
    	else 
    	{
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   		
    	}

    }

    @GetMapping("/getPhysicalUser")
    public ResponseEntity<PhysicalUserDTO> getPhysicalUser(@RequestParam String username) {
        
    	
    	if(physicalUserService.existsUser(username))
    	{
    		Physical user = physicalUserService.findByUsername(username);
    			    	
            return new ResponseEntity<>(user.toView(), HttpStatus.OK);
    	}
    	else 
    	{
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   		
    	}

    }

    
    
}
