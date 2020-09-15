package com.banco.api.controller;

import com.banco.api.dto.user.LegalUserDTO;
import com.banco.api.dto.user.PhysicalUserDTO;
import com.banco.api.dto.user.UserDTO;
import com.banco.api.dto.user.request.*;
import com.banco.api.dto.user.request.modification.LegalUserModificationRequest;
import com.banco.api.dto.user.request.modification.PhysicalUserModificationRequest;
import com.banco.api.exception.InvalidUserRequestException;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.user.Legal;
import com.banco.api.model.user.Physical;
import com.banco.api.service.account.CheckingService;
import com.banco.api.service.account.SavingsService;
import com.banco.api.service.user.AdministrativeUserService;
import com.banco.api.service.user.LegalUserService;
import com.banco.api.service.user.PhysicalUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private PhysicalUserService physicalUserService;
    @Autowired
    private LegalUserService legalUserService;
    @Autowired
    private AdministrativeUserService administrativeUserService;
    
    @Autowired
    private SavingsService savingsService;
    @Autowired
    private CheckingService checkingService;

    @PostMapping("/physical")
    public ResponseEntity createPhysical(@RequestBody PhysicalUserRequest request) {
		LOGGER.info("Create physical user operation started. {}", request.toString());

        try {
            return new ResponseEntity<>(physicalUserService.createUser(request), HttpStatus.CREATED);
        } catch (InvalidUserRequestException ex) {
            LOGGER.warn(ex.getLocalizedMessage());
            return ResponseEntity
                    .status(HttpStatus.IM_USED)
                    .body("{\"error\": \"" + ex.getLocalizedMessage() + "\"}");
        }
    }
    
    @PostMapping("/administrative")
    public ResponseEntity createAdministrative(@RequestBody AdministrativeUserRequest request) {
		LOGGER.info("Create administrative user operation started. {}", request.toString());

        try {
            return new ResponseEntity<>(administrativeUserService.createUser(request), HttpStatus.CREATED);
        } catch (InvalidUserRequestException ex) {
            LOGGER.warn(ex.getLocalizedMessage());
            return ResponseEntity
                    .status(HttpStatus.IM_USED)
                    .body("{\"error\": \"" + ex.getLocalizedMessage() + "\"}");
        }
    }

    @PostMapping("/legal")
    public ResponseEntity createLegal(@RequestBody LegalUserRequest request) {
        LOGGER.info("Create legal user operation started. {}", request.toString());

        try {
            return new ResponseEntity<>(legalUserService.createUser(request), HttpStatus.CREATED);
        } catch (InvalidUserRequestException ex) {
            LOGGER.warn(ex.getLocalizedMessage());
            return ResponseEntity
                    .status(HttpStatus.IM_USED)
                    .body("{\"error\": \"" + ex.getLocalizedMessage() + "\"}");
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<Integer> login(@RequestBody LoginRequest request) { //0, 1 o 2, según si es persona fisica, juridica o administrativa
    	int login = 2;
    	
    	if(physicalUserService.findByUsername(request.getUsername()) != null) {
    		login = physicalUserService.login(request.getUsername(), request.getPassword());
	    	switch (login) {
			case 1:
				LOGGER.info("Successfully logued with credentials: {}", request.toString());
	            return new ResponseEntity<>(0,HttpStatus.OK);
			case 2:
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			case 3:
				LOGGER.info("First login successful with credentials: {}", request.toString());
	            return new ResponseEntity<>(0,HttpStatus.ACCEPTED); // First Login
			default:
				return new ResponseEntity<>(HttpStatus.CONFLICT);			
	    	}
    	}
    	else if(legalUserService.findByUsername(request.getUsername()) != null) {
    		login = legalUserService.login(request.getUsername(), request.getPassword());
    	}
    	else if(administrativeUserService.findByUsername(request.getUsername()) != null) {
    		login = administrativeUserService.login(request.getUsername(), request.getPassword());
    	}
    	else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
    	
    	switch (login) {
		case 1:
			LOGGER.info("Successfully logued with credentials: {}", request.toString());
            return new ResponseEntity<>(0,HttpStatus.OK);
		case 2:
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		case 3:
			LOGGER.info("First login successful with credentials: {}", request.toString());
            return new ResponseEntity<>(0,HttpStatus.ACCEPTED); // First Login
		default:
			return new ResponseEntity<>(HttpStatus.CONFLICT);			
    	}
    }
    
    @PostMapping("/changePassword")
    public ResponseEntity changePassword(@RequestBody ChangePasswordRequest request){
    	if(legalUserService.existsUser(request.getUsername())) {
    		legalUserService.changePassword(request);
    		LOGGER.info("Successfully changed password: {}", request.toString());
			return new ResponseEntity<>(HttpStatus.OK);
    	}
    	else if(physicalUserService.existsUser(request.getUsername())) {
    		physicalUserService.changePassword(request);
    		LOGGER.info("Successfully changed password: {}", request.toString());
			return new ResponseEntity<>(HttpStatus.OK);
    	}
    	else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
    	}
    }

	@GetMapping("/physical")
	public ResponseEntity<PhysicalUserDTO> getPhysicalUser(@RequestParam String username) {
		Physical user = physicalUserService.findByUsername(username);

        if (user != null) {
            return new ResponseEntity<>(user.toView(), HttpStatus.OK);
        } else {
            String message = String.format("User not found with username: %s", username);
            LOGGER.warn(message);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/legal")
    public ResponseEntity<LegalUserDTO> getLegalUser(@RequestParam String username) {
    	Legal user = legalUserService.findByUsername(username);

        if (user != null) {
            return new ResponseEntity<>(user.toView(), HttpStatus.OK);
        } else {
            String message = String.format("User not found with username: %s", username);
            LOGGER.warn(message);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/physical/modify")
    public ResponseEntity modifyPhysical(@RequestBody PhysicalUserModificationRequest request) {
    	try {
	        LOGGER.info("Modification of physical user operation started. {}", request.toString());
	        return new ResponseEntity<PhysicalUserDTO>(physicalUserService.modify(request),HttpStatus.OK);
    	} 
    	catch (InvalidUserRequestException ex) {
	        LOGGER.warn(ex.getLocalizedMessage());
	        return ResponseEntity
	                .status(HttpStatus.IM_USED)
	                .body("{\"error\": \"" + ex.getLocalizedMessage() + "\"}");
    	}
    }
    
    @PostMapping("/legal/modify")
    public ResponseEntity modifyLegal(@RequestBody LegalUserModificationRequest request) {
    	try {
	        LOGGER.info("Modification of legal user operation started. {}", request.toString());
	        return new ResponseEntity<LegalUserDTO>(legalUserService.modify(request),HttpStatus.OK);
    	} 
    	catch (InvalidUserRequestException ex) {
	        LOGGER.warn(ex.getLocalizedMessage());
	        return ResponseEntity
	                .status(HttpStatus.IM_USED)
	                .body("{\"error\": \"" + ex.getLocalizedMessage() + "\"}");
    	}
    }
    

    @GetMapping("/physical/search")
    public ResponseEntity<Set<PhysicalUserDTO>> searchPhysicalUSer(@RequestParam String field, @RequestParam String term) {
        LOGGER.info("Search physical user operation started. Field: %s, term: %s", field, term);
        Set<PhysicalUserDTO> users = physicalUserService.search(field, term);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/legal/search")
    public ResponseEntity<Set<LegalUserDTO>> searchLegalUser(@RequestParam String field, @RequestParam String term) {
        LOGGER.info("Search legal user operation started. Field: %s, term: %s", field, term);
        Set<LegalUserDTO> users = legalUserService.search(field, term);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @DeleteMapping("/disable-user")
    public ResponseEntity<UserDTO> disableUser(@RequestParam String username) {

    	if(physicalUserService.existsUser(username))
    	{
    		Physical physical = physicalUserService.findByUsername(username);

    		Savings savings = physical.getSavings();
    		Savings savingsResult = savingsService.closeAccount(savings);
    		if(savingsResult.isActive())
    		{
    			//La caja de ahorro no tiene balance 0, y no se puede cerrar.
    	        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    		}
    		else {
	    		Checking checking = physical.getChecking();

	    		if(checking != null && checking.isActive())
	    		{
	        		Checking checkingResult = checkingService.closeAccount(checking);
	        		if(checkingResult.isActive())
	        		{
	        			//La cuenta corriente no tiene balance 0, y no se puede cerrar.
	        	        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

	        		}
	    		}

	    		physical.setActive(false);
	    		PhysicalUserDTO result = physicalUserService.update(physical);

		    	return new ResponseEntity<>(result, HttpStatus.OK);
    		}
    	}
    	else if(legalUserService.existsUser(username))
    	{
    		Legal legal = legalUserService.findByUsername(username);

    		Savings savings = legal.getSavings();
    		Savings savingsResult = savingsService.closeAccount(savings);
    		if(savingsResult.isActive())
    		{
    			//La caja de ahorro no tiene balance 0, y no se puede cerrar.
    	        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    		}
    		else {
	    		Checking checking = legal.getChecking();

	    		if(checking != null && checking.isActive())
	    		{
	        		Checking checkingResult = checkingService.closeAccount(checking);
	        		if(checkingResult.isActive())
	        		{
	        			//La cuenta corriente no tiene balance 0, y no se puede cerrar.
	        	        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

	        		}
	    		}

	    		legal.setActive(false);
	    		LegalUserDTO result = legalUserService.update(legal);

		    	return new ResponseEntity<>(result, HttpStatus.OK);
    		}
    	}
    	else
    	{
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }

    @PutMapping("/reset-password")
    public ResponseEntity<UserDTO> resetPassword(@RequestParam String username) {

    	if(physicalUserService.existsUser(username))
    	{
    		PhysicalUserDTO result = physicalUserService.resetPassword(username);
		    return new ResponseEntity<>(result, HttpStatus.OK);
    	}
    	else if(legalUserService.existsUser(username))
    	{
    		LegalUserDTO result = legalUserService.resetPassword(username);
		    return new ResponseEntity<>(result, HttpStatus.OK);
    	}
    	else
    	{
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }

}
