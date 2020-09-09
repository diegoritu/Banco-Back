package com.banco.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banco.api.dto.account.CheckingDTO;
import com.banco.api.dto.account.SavingsDTO;
import com.banco.api.dto.user.LegalUserDTO;
import com.banco.api.dto.user.PhysicalUserDTO;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.user.Legal;
import com.banco.api.model.user.Physical;
import com.banco.api.service.account.CheckingService;
import com.banco.api.service.account.SavingsService;
import com.banco.api.service.user.LegalUserService;
import com.banco.api.service.user.PhysicalUserService;

@RestController
@RequestMapping("/account")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
    
   
    @Autowired
    private PhysicalUserService physicalUserService;
    @Autowired
    private LegalUserService legalUserService;
    @Autowired
    private CheckingService checkingService;
    @Autowired
    private SavingsService savingsService;

    
    @PostMapping("/new-checking")
    public ResponseEntity<CheckingDTO> createChecking(@RequestParam String username) {
    	
    	boolean existsUserLegal = legalUserService.existsUser(username);
    	boolean existsUserPhysical = physicalUserService.existsUser(username);


		if (existsUserPhysical) {
    			//Physical
    			Physical physicalUser = physicalUserService.findByUsername(username);
    			if(physicalUser.getChecking() != null) 
    			{
    				Checking checking = physicalUser.getChecking();
    				
    				if(checking.isActive()) 
    				{
        				//Error
        		        return new ResponseEntity<>(HttpStatus.OK);
    				}
    				else 
    				{
        		    	LOGGER.info("Opening checking account {}", username);
    					checking.setActive(true);
    					Checking result = checkingService.update(checking);
        		        return new ResponseEntity<>(result.toView(), HttpStatus.CREATED);

				}
			} else {
				LOGGER.info("Creating checking account {}", username);
				Checking checking = checkingService.createAccount();

				physicalUser.setChecking(checking);

				PhysicalUserDTO physicalResult = physicalUserService.update(physicalUser);

				return new ResponseEntity<>(checking.toView(), HttpStatus.CREATED);

			}

		} else if (existsUserLegal) {
    			//Legal
    			Legal legalUser = legalUserService.findByUsername(username);
    			if(legalUser.getChecking() != null)
    			{
    				Checking checking = legalUser.getChecking();

    				if(checking.isActive())
    				{
        				//Error
        		        return new ResponseEntity<>(HttpStatus.OK);
    				}
    				else
    				{
        		    	LOGGER.info("Opening checking account {}", username);
    					checking.setActive(true);
    					Checking result = checkingService.update(checking);
        		        return new ResponseEntity<>(result.toView(), HttpStatus.CREATED);

    				}
    			}
    			else
    			{
    		    	LOGGER.info("Creating checking account {}", username);
    		    	Checking checking = checkingService.createAccount();

    		    	legalUser.setChecking(checking);

    		    	LegalUserDTO legalResult = legalUserService.update(legalUser);

    		        return new ResponseEntity<>(checking.toView(), HttpStatus.CREATED);

    			}
		} else {
		 	LOGGER.error("Could not create new checking account. Physical or legal user not found with username: %s", username);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

    }

    
    @DeleteMapping("/close-checking")
    public ResponseEntity<CheckingDTO> closeChecking(@RequestParam String username) {
    	
    	boolean existsUserLegal = legalUserService.existsUser(username);
    	boolean existsUserPhysical = physicalUserService.existsUser(username);


		if (existsUserPhysical) {
    			//Physical
    			Physical physicalUser = physicalUserService.findByUsername(username);
    			if(physicalUser.getChecking() != null) 
    			{
    		    	LOGGER.info("Closing checking account {}", username);
    		    	Checking checking = checkingService.closeAccount(physicalUser.getChecking());
    		    	
    		    	if(checking.isActive())
    		    	{
    		    		//No se cerró la cuenta porque el balance no daba 0
        		        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    		    	}
    		    	else 
    		    	{
        		        return new ResponseEntity<>(checking.toView(), HttpStatus.OK);    		    		
    		    	}
    		    	

    			}
    			else 
    			{
    				//Error
    		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    			}
		} else if (existsUserLegal) {
    			//Legal
    			Legal legalUser = legalUserService.findByUsername(username);
    			if(legalUser.getChecking() != null) 
    			{
    		    	LOGGER.info("Closing checking account {}", username);
    		    	Checking checking = checkingService.closeAccount(legalUser.getChecking());
    		    	
    		    	if(checking.isActive())
    		    	{
    		    		//No se cerró la cuenta porque el balance no daba 0
        		        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    		    	}
    		    	else 
    		    	{
        		        return new ResponseEntity<>(checking.toView(), HttpStatus.OK);    		    		
    		    	}

    			}
    			else 
    			{
    				//Error
    		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    			}
    		} else {
    			//Error: Es usuario administrativo, y por lo tanto no tiene ninguna cuenta asociada. O no existe un usuario con tal username
				LOGGER.error("Could not create new checking account. Physical or legal user not found with username: %s", username);
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    		}

    }

    
    @GetMapping("/checking")
    public ResponseEntity<CheckingDTO> getCheckingAccount(@RequestParam String accountNumber) {
		Checking checking = checkingService.findByAccountNumber(accountNumber);

		if (checking != null) {
            return new ResponseEntity<>(checking.toView(), HttpStatus.OK);
    	} else {
			LOGGER.warn("Checking account not found with account number: %s", accountNumber);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}

    }

    @GetMapping("/savings")
    public ResponseEntity<SavingsDTO> getSavingsAccount(@RequestParam String accountNumber) {
        Savings savings = savingsService.findByAccountNumber(accountNumber);
    	
    	if (savings != null) {
            return new ResponseEntity<>(savings.toView(), HttpStatus.OK);
    	} else {
			LOGGER.warn("Savings account not found with account number: %s", accountNumber);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}

    }

    
}
