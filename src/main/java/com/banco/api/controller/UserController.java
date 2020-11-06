package com.banco.api.controller;

import com.banco.api.dto.user.LegalUserDTO;
import com.banco.api.dto.user.PhysicalUserDTO;
import com.banco.api.dto.user.UserDTO;
import com.banco.api.dto.user.request.*;
import com.banco.api.dto.user.request.modification.LegalUserModificationRequest;
import com.banco.api.dto.user.request.modification.PhysicalUserModificationRequest;
import com.banco.api.exception.DuplicatedUserException;
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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.banco.api.controller.ResponseEntityFactory.createErrorResponseEntity;

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
        } catch (DuplicatedUserException ex) {
        	LOGGER.warn(ex.getLocalizedMessage());
        	return createErrorResponseEntity(ex.getLocalizedMessage(), HttpStatus.CONFLICT);

		} catch (InvalidUserRequestException ex) {
            LOGGER.warn(ex.getLocalizedMessage());
            return createErrorResponseEntity(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/administrative")
    public ResponseEntity createAdministrative(@RequestBody AdministrativeUserRequest request) {
		LOGGER.info("Create administrative user operation started. {}", request.toString());

        try {
            return new ResponseEntity<>(administrativeUserService.createUser(request), HttpStatus.CREATED);
        } catch (DuplicatedUserException ex) {
            LOGGER.warn(ex.getLocalizedMessage());
            return createErrorResponseEntity(ex.getLocalizedMessage(), HttpStatus.CONFLICT);
		}
    }

    @PostMapping("/legal")
    public ResponseEntity createLegal(@RequestBody LegalUserRequest request) {
        LOGGER.info("Create legal user operation started. {}", request.toString());

        try {
            return new ResponseEntity<>(legalUserService.createUser(request), HttpStatus.CREATED);
		} catch (DuplicatedUserException ex) {
			LOGGER.warn(ex.getLocalizedMessage());
			return createErrorResponseEntity(ex.getLocalizedMessage(), HttpStatus.CONFLICT);

		} catch (InvalidUserRequestException ex) {
			LOGGER.warn(ex.getLocalizedMessage());
			return createErrorResponseEntity(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
    }
    
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest request) { //0, 1 o 2, según si es persona fisica, juridica o administrativa
    	int login = 2;
    	if(physicalUserService.findByActiveUsername(request.getUsername()) != null) {
    		login = physicalUserService.login(request.getUsername(), request.getPassword());

	    	switch (login) {
			case 1:
				LOGGER.info("Successfully logued with credentials: {}", request.toString());
	            return new ResponseEntity<>(physicalUserService.findByUsername(request.getUsername()).toView(),HttpStatus.OK);
			case 2:
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			case 3:
				LOGGER.info("First login successful with credentials: {}", request.toString());
	            return new ResponseEntity<>(physicalUserService.findByUsername(request.getUsername()).toView(),HttpStatus.ACCEPTED); // First Login
			default:
				return new ResponseEntity<>(HttpStatus.CONFLICT);			
	    	}
    	}
    	else if(legalUserService.findByActiveUsername(request.getUsername()) != null) {
    		login = legalUserService.login(request.getUsername(), request.getPassword());

        	switch (login) {
    		case 1:
    			LOGGER.info("Successfully logued with credentials: {}", request.toString());
                return new ResponseEntity<>(legalUserService.findByUsername(request.getUsername()).toView(),HttpStatus.OK);
    		case 2:
    			return new ResponseEntity<>(HttpStatus.CONFLICT);
    		case 3:
    			LOGGER.info("First login successful with credentials: {}", request.toString());
                return new ResponseEntity<>(legalUserService.findByUsername(request.getUsername()).toView(),HttpStatus.ACCEPTED); // First Login
    		default:
    			return new ResponseEntity<>(HttpStatus.CONFLICT);			
        	}

    	}
    	else if(administrativeUserService.findByActiveUsername(request.getUsername()) != null) {
    		login = administrativeUserService.login(request.getUsername(), request.getPassword());

        	switch (login) {
    		case 1:
    			LOGGER.info("Successfully logued with credentials: {}", request.toString());
                return new ResponseEntity<>(administrativeUserService.findByUsername(request.getUsername()).toView(),HttpStatus.OK);
    		case 2:
    			return new ResponseEntity<>(HttpStatus.CONFLICT);
    		case 3:
    			LOGGER.info("First login successful with credentials: {}", request.toString());
                return new ResponseEntity<>(administrativeUserService.findByUsername(request.getUsername()).toView(),HttpStatus.ACCEPTED); // First Login
    		default:
    			return new ResponseEntity<>(HttpStatus.CONFLICT);			
        	}

    	}
    	else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
    	
    }
    
    @PostMapping("/changePassword")
    public ResponseEntity changePassword(@RequestBody ChangePasswordRequest request){
    	if(legalUserService.existsByUsername(request.getUsername())) {
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
    
    @GetMapping("/legals")
    public ResponseEntity<List<LegalUserDTO>> getLegalUsers() {
    	List<Legal> users = legalUserService.findAllLegals();
    	List<LegalUserDTO> legalsView = users.stream().map(Legal::toView).collect(Collectors.toList());
        return new ResponseEntity<>(legalsView, HttpStatus.OK);
        
    }
    
    @PutMapping("/physical/modify")
    public ResponseEntity modifyPhysical(@RequestBody PhysicalUserModificationRequest request) {
    	try {
	        LOGGER.info("Modification of physical user operation started. {}", request.toString());
	        return new ResponseEntity<PhysicalUserDTO>(physicalUserService.modify(request),HttpStatus.OK);
    	} 
    	catch (DuplicatedUserException ex) {
	        LOGGER.warn(ex.getLocalizedMessage());
	        return createErrorResponseEntity(ex.getLocalizedMessage(), HttpStatus.CONFLICT);

		}
    }
    
    @PutMapping("/legal/modify")
    public ResponseEntity modifyLegal(@RequestBody LegalUserModificationRequest request) {
    	try {
	        LOGGER.info("Modification of legal user operation started. {}", request.toString());
	        return new ResponseEntity<LegalUserDTO>(legalUserService.modify(request),HttpStatus.OK);
    	} 
    	catch (DuplicatedUserException ex) {
	        LOGGER.warn(ex.getLocalizedMessage());
	        return createErrorResponseEntity(ex.getLocalizedMessage(), HttpStatus.CONFLICT);
    	}
    }
    

    @GetMapping("/physical/search")
    public ResponseEntity<Set<PhysicalUserDTO>> searchPhysicalUSer(@RequestParam String field, @RequestParam String term) {
        LOGGER.info("Search physical user operation started. Field: {}, term: {}", field, term);
        Set<PhysicalUserDTO> users = physicalUserService.search(field, term);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/legal/search")
    public ResponseEntity<Set<LegalUserDTO>> searchLegalUser(@RequestParam String field, @RequestParam String term) {
        LOGGER.info("Search legal user operation started. Field: {}, term: {}", field, term);
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
    	else if(legalUserService.existsByUsername(username))
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
    	else if(legalUserService.existsByUsername(username))
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
