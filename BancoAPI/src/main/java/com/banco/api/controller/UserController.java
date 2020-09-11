package com.banco.api.controller;

import com.banco.api.dto.user.LegalUserDTO;
import com.banco.api.dto.user.PhysicalUserDTO;
import com.banco.api.dto.user.UserDTO;
import com.banco.api.dto.user.request.AdministrativeUserRequest;
import com.banco.api.dto.user.request.LegalUserRequest;
import com.banco.api.dto.user.request.LoginRequest;
import com.banco.api.dto.user.request.PhysicalUserRequest;
import com.banco.api.exception.DuplicatedUsernameException;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.user.Legal;
import com.banco.api.model.user.Physical;
import com.banco.api.model.user.User;
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
        } catch (DuplicatedUsernameException ex) {
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
        } catch (DuplicatedUsernameException ex) {
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
        } catch (DuplicatedUsernameException ex) {
            LOGGER.warn(ex.getLocalizedMessage());
            return ResponseEntity
                    .status(HttpStatus.IM_USED)
                    .body("{\"error\": \"" + ex.getLocalizedMessage() + "\"}");
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest request) {
    	if(physicalUserService.findByUsername(request.getUsername()) != null) {
    		Physical user = physicalUserService.findByUsername(request.getUsername());
    		if(physicalUserService.login(request.getUsername(), request.getPassword()) == 1) {
    	        LOGGER.info("Successfully logued with credentials: {}", request.toString());
                return new ResponseEntity<>(HttpStatus.OK);
    		}
    		else if(physicalUserService.login(request.getUsername(), request.getPassword()) == 3) {
    			LOGGER.info("First login successful with credentials: {}", request.toString());
                return new ResponseEntity<>(HttpStatus.ACCEPTED); // First Login
    		}
    		else {
    			return new ResponseEntity<>(HttpStatus.CONFLICT);
    		}
    	}
    	else if(legalUserService.findByUsername(request.getUsername()) != null) {
    		if(legalUserService.login(request.getUsername(), request.getPassword()) == 1) {
    			LOGGER.info("Successfully logued with credentials: {}", request.toString());
    			return new ResponseEntity<>(HttpStatus.OK);
    		}
    		else if(legalUserService.login(request.getUsername(), request.getPassword()) == 3) {
    			LOGGER.info("First login successful with credentials: {}", request.toString());
                return new ResponseEntity<>(HttpStatus.ACCEPTED); // First Login
    		}
    		else {
    			return new ResponseEntity<>(HttpStatus.CONFLICT);
    		}
    	}
    	else if(administrativeUserService.findByUsername(request.getUsername()) != null) {
    		if(administrativeUserService.login(request.getUsername(), request.getPassword()) == 1) {
    			LOGGER.info("Successfully logued with credentials: {}", request.toString());
    			return new ResponseEntity<>(HttpStatus.OK);
    		}
    		else if(administrativeUserService.login(request.getUsername(), request.getPassword()) == 3) {
    			LOGGER.info("First login successful with credentials: {}", request.toString());
                return new ResponseEntity<>(HttpStatus.ACCEPTED); // First Login
    		}
    		else {
    			return new ResponseEntity<>(HttpStatus.CONFLICT);
    		}
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

    @GetMapping("/physical/search")
    public ResponseEntity<PhysicalUserDTO> searchPhysicalUSer(@RequestParam String field, @RequestParam String term) {
        LOGGER.info("Search physical user operation started. Field: %s, term: %s", field, term);
        PhysicalUserDTO user = physicalUserService.search(field, term);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            String message = String.format("Physical user not found with field: %s and search term %s", field, term);
            LOGGER.info(message);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/legal/search")
    public ResponseEntity<LegalUserDTO> searchLegalUser(@RequestParam String field, @RequestParam String term) {
        LOGGER.info("Search legal user operation started. Field: %s, term: %s", field, term);
        LegalUserDTO user = legalUserService.search(field, term);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            String message = String.format("Legal user not found with field: %s and search term %s", field, term);
            LOGGER.info(message);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //FALTA PROBARLO Y VERIFICAR QUE LOS BALANCES DE LAS CUENTAS DEN 0
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


}
