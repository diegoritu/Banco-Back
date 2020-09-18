package com.banco.api.controller;

import com.banco.api.dto.account.CheckingDTO;
import com.banco.api.dto.account.SavingsDTO;
import com.banco.api.dto.account.request.CreateCheckingAccountRequest;
import com.banco.api.dto.account.request.UpdateCheckingAccountRequest;
import com.banco.api.exception.CheckingAccountRequestException;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.user.Legal;
import com.banco.api.model.user.Physical;
import com.banco.api.service.account.CheckingService;
import com.banco.api.service.account.SavingsService;
import com.banco.api.service.user.LegalUserService;
import com.banco.api.service.user.PhysicalUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.banco.api.controller.ResponseEntityFactory.createErrorResponseEntity;

@CrossOrigin(origins = "*")
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

	@PostMapping("/checking")
	public ResponseEntity createCheckingAccount(@RequestBody CreateCheckingAccountRequest request) {
		String username = request.getUsername();
		Float maxOverdraft = request.getMaxOverdraft();
		Checking checking;
		try {
			if (legalUserService.existsUser(username)) {
				checking = legalUserService.openCheckingAccount(username, maxOverdraft);
			} else if (physicalUserService.existsUser(username)) {
				checking = physicalUserService.openCheckingAccount(username, maxOverdraft);
			} else {
				LOGGER.warn("Could not create new checking account. Physical or legal user not found with username: %s", username);
				return createErrorResponseEntity(HttpStatus.NOT_FOUND, "No se pudo crear la cuenta corriente. Usuario no encontrado");
			}
		} catch (CheckingAccountRequestException ex) {
			LOGGER.warn(ex.getLocalizedMessage());
			return createErrorResponseEntity(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
		}
		LOGGER.info("Successfully opened checking account for username: {}. Account number: {}", username, checking.getAccountNumber());
		return new ResponseEntity<>(checking.toView(), HttpStatus.CREATED);
	}


	@PutMapping("/checking")
	public ResponseEntity updateCheckingAccount(@PathVariable UpdateCheckingAccountRequest request) {
		Checking checking = checkingService.findByAccountNumber(request.getAccountNumber());
		if (checking == null || !checking.isActive()) {
			String message = "Could not update checking account max overdraft. Account number {} not found";
			LOGGER.warn("Could not update checking account max overdraft. Account number {} not found", request.getAccountNumber());
			return createErrorResponseEntity(HttpStatus.NOT_FOUND, message);
		} else {
			checking.setMaxOverdraft(request.getMaxOverDraft());
			Checking result = checkingService.update(checking);
			return new ResponseEntity<>(result.toView(), HttpStatus.OK);
		}
	}
	
    
    @DeleteMapping("/checking/close-account")
    public ResponseEntity<CheckingDTO> closeChecking(@RequestParam String username) {

		boolean existsUserLegal = legalUserService.existsUser(username);
		boolean existsUserPhysical = physicalUserService.existsUser(username);


		if (existsUserPhysical) {
			//Physical
			Physical physicalUser = physicalUserService.findByUsername(username);
			if (physicalUser.getChecking() != null) {
				LOGGER.info("Closing checking account {}", username);
				Checking checking = checkingService.closeAccount(physicalUser.getChecking());

				if (checking.isActive()) {
					//No se cerró la cuenta porque el balance no daba 0
					return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

				} else {
					return new ResponseEntity<>(checking.toView(), HttpStatus.OK);
				}


			} else {
				//Error
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);


			}
		} else if (existsUserLegal) {
			//Legal
			Legal legalUser = legalUserService.findByUsername(username);
			if (legalUser.getChecking() != null) {
				LOGGER.info("Closing checking account {}", username);
				Checking checking = checkingService.closeAccount(legalUser.getChecking());

				if (checking.isActive()) {
					//No se cerró la cuenta porque el balance no daba 0
					return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

				} else {
					return new ResponseEntity<>(checking.toView(), HttpStatus.OK);
				}

			} else {
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
