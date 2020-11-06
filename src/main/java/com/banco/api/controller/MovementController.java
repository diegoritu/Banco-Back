package com.banco.api.controller;

import com.banco.api.dto.movement.MovementDTO;
import com.banco.api.dto.movement.MovementType;
import com.banco.api.dto.movement.request.DepositAndExtractionRequest;
import com.banco.api.dto.movement.request.ServicePaymentRequest;
import com.banco.api.dto.movement.request.TransferBetweenOwnAccountsRequest;
import com.banco.api.dto.movement.request.TransferToOtherAccountsRequest;
import com.banco.api.model.ServicePayment;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.user.Legal;
import com.banco.api.model.user.Physical;
import com.banco.api.service.billService.BillService;
import com.banco.api.service.MovementService;
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

import java.util.Collection;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/movement")
public class MovementController {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(MovementController.class);
    @Autowired
    private SavingsService savingsService;
    @Autowired
    private CheckingService checkingService;
    @Autowired
    private MovementService movementService;
    @Autowired
    private BillService billService;
    @Autowired
    private LegalUserService legalUserService;
    @Autowired
    private PhysicalUserService physicalUserService;
    
    
    
    @PostMapping("/deposit")
    public ResponseEntity<MovementDTO> deposit(@RequestBody DepositAndExtractionRequest request){
    	float balanceBeforeMovement;
    	LOGGER.info("Adding movement: {}", request.toString());
    	if(savingsService.existsAccountNumber(request.getAccountNumberEntryAccount())) {
    		Savings savingsEntry = savingsService.findByAccountNumber(request.getAccountNumberEntryAccount());
    		balanceBeforeMovement = savingsEntry.getBalance();
    		savingsEntry.deposit(request.getAmount());
    		return new ResponseEntity<MovementDTO>(movementService.deposit(request.getAmount(), balanceBeforeMovement,
                    savingsEntry, MovementType.DEPOSIT), HttpStatus.OK);
//    		return new ResponseEntity<MovementDTO>(movementService.depositAndExtract(request.getAmount(), balanceBeforeMovement, AccountType.SAVINGS, savingsEntry, null, MovementType.DEPOSIT),HttpStatus.OK);
    	}
    	
    	else if(checkingService.existsAccountNumber(request.getAccountNumberEntryAccount())){
    		Checking checkingEntry = checkingService.findByAccountNumber(request.getAccountNumberEntryAccount());
    		balanceBeforeMovement = checkingEntry.getBalance();
    		checkingEntry.deposit(request.getAmount());
    		return new ResponseEntity<MovementDTO>(movementService.deposit(request.getAmount(), balanceBeforeMovement,
                    checkingEntry, MovementType.DEPOSIT), HttpStatus.OK);
//    		return new ResponseEntity<MovementDTO>(movementService.depositAndExtract(request.getAmount(), balanceBeforeMovement, AccountType.CHECKING, null, checkingEntry, MovementType.DEPOSIT),HttpStatus.OK);
    	}
    	
    	else {
    		return new ResponseEntity<>(HttpStatus.CONFLICT);
    	}
    }
    
    @PostMapping("/extract")
    public ResponseEntity<MovementDTO> extract(@RequestBody DepositAndExtractionRequest request){
    	float balanceBeforeMovement;
    	boolean canBePerformed;
    	LOGGER.info("Adding movement: {}", request.toString());
    	if(savingsService.existsAccountNumber(request.getAccountNumberEntryAccount())) {
    		Savings savingsExit = savingsService.findByAccountNumber(request.getAccountNumberEntryAccount());
    		balanceBeforeMovement = savingsExit.getBalance();
    		canBePerformed = savingsExit.extract(request.getAmount());
    		if(canBePerformed) {
    		    return new ResponseEntity<MovementDTO>(movementService.extract(request.getAmount(), balanceBeforeMovement,
                        savingsExit, MovementType.EXTRACTION), HttpStatus.OK);
//    			return new ResponseEntity<MovementDTO>(movementService.depositAndExtract(request.getAmount(), balanceBeforeMovement, AccountType.SAVINGS, savingsExit, null, MovementType.EXTRACTION),HttpStatus.OK);
    		}
    		else {
    			return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    		}
    	}
    	
    	else if(checkingService.existsAccountNumber(request.getAccountNumberEntryAccount())){
    		Checking checkingExit = checkingService.findByAccountNumber(request.getAccountNumberEntryAccount());
    		balanceBeforeMovement = checkingExit.getBalance();
    		canBePerformed = checkingExit.extract(request.getAmount());
    		if(canBePerformed) {
    		    return new ResponseEntity<MovementDTO>(movementService.extract(request.getAmount(), balanceBeforeMovement,
                        checkingExit, MovementType.EXTRACTION), HttpStatus.OK);
//    			return new ResponseEntity<MovementDTO>(movementService.depositAndExtract(request.getAmount(), balanceBeforeMovement, AccountType.CHECKING, null, checkingExit, MovementType.EXTRACTION),HttpStatus.OK);
    		}
    		else {
    			return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    		}
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.CONFLICT);
    	}
    }
    
    @GetMapping("/movements")
    public ResponseEntity<Collection<MovementDTO>> getMovements(@RequestParam String accountNumber){
    	byte accountType;
    	if(checkingService.existsAccountNumber(accountNumber)) {
    		accountType = 0;
    	}
    	else {
    		accountType = 1;
    	}
    	return new ResponseEntity<Collection<MovementDTO>>(movementService.getMovements(accountNumber, accountType), HttpStatus.OK);
    }
    
    @GetMapping("/movementById")
    public ResponseEntity<MovementDTO> getMovementById(@RequestParam int id){    	
    	return new ResponseEntity<MovementDTO>(movementService.getMovementById(id), HttpStatus.OK);
    }
    
    @PostMapping("/transferBetweenOwnAccounts")
    public ResponseEntity<MovementDTO> transferBetweenOwnAccounts(@RequestBody TransferBetweenOwnAccountsRequest request){
    	float balanceBeforeMovementFrom;
    	float balanceBeforeMovementTo;
    	Savings savingsFrom = null;
    	Checking checkingFrom = null;
    	Savings savingsTo = null;
    	Checking checkingTo = null;
    	boolean canBePerformed;
    	boolean fromSavings; //If the account from which the amount is extracted is a savings account, the value will be true. Otherwise, it will be false.
    	
    	if(savingsService.existsAccountNumber(request.getAccountNumberFrom())) {
    		savingsFrom = savingsService.findByAccountNumber(request.getAccountNumberFrom());
    		balanceBeforeMovementFrom = savingsFrom.getBalance();
    		fromSavings = true;
    	}  	
    	else if(checkingService.existsAccountNumber(request.getAccountNumberFrom())){
    		checkingFrom = checkingService.findByAccountNumber(request.getAccountNumberFrom());
    		balanceBeforeMovementFrom = checkingFrom.getBalance();
    		fromSavings = false;
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.CONFLICT);
    	}
    	if(savingsService.existsAccountNumber(request.getAccountNumberTo())) {
    		savingsTo = savingsService.findByAccountNumber(request.getAccountNumberTo());
    		balanceBeforeMovementTo = savingsTo.getBalance();
    	}
    	else if(checkingService.existsAccountNumber(request.getAccountNumberTo())) {
    		checkingTo = checkingService.findByAccountNumber(request.getAccountNumberTo());
    		balanceBeforeMovementTo = checkingTo.getBalance();
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.CONFLICT);
    	}
    	
    	if(fromSavings) {
    		canBePerformed = savingsFrom.extract(request.getAmount());
    		if(canBePerformed) {
    			checkingTo.deposit(request.getAmount());
    			return new ResponseEntity<MovementDTO>(movementService.transferBetweenOwnAccounts(request.getAmount(), balanceBeforeMovementFrom, balanceBeforeMovementTo, savingsFrom, null, null, checkingTo, true),HttpStatus.OK);
    		}
    		else {
    			return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    		}
    	}
    	else {
    		canBePerformed = checkingFrom.extract(request.getAmount());
    		if(canBePerformed) {
    			savingsTo.deposit(request.getAmount());
    			return new ResponseEntity<MovementDTO>(movementService.transferBetweenOwnAccounts(request.getAmount(), balanceBeforeMovementFrom, balanceBeforeMovementTo, null, savingsTo, checkingFrom, null, false),HttpStatus.OK);
    		}
    		else {
    			return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    		}
    	}	
    }
    
    @PostMapping("/transferToOtherAccounts")
    public ResponseEntity<MovementDTO> transferToOtherAccounts(@RequestBody TransferToOtherAccountsRequest request){
    	float balanceBeforeMovementFrom;
    	float balanceBeforeMovementTo;
    	Savings savingsFrom = null;
    	Checking checkingFrom = null;
    	Savings savingsTo = null;
    	Checking checkingTo = null;
    	boolean canBePerformed;
    	int whereFrom = 0; // 1 = from savings to savings; 2= from savings to checking; 3= from checking to savings; 4= from checking to checking.

    	if(savingsService.existsAccountNumber(request.getAccountNumberFrom())) {
    		savingsFrom = savingsService.findByAccountNumber(request.getAccountNumberFrom());
    		balanceBeforeMovementFrom = savingsFrom.getBalance();
    	}  	
    	else if(checkingService.existsAccountNumber(request.getAccountNumberFrom())){
    		checkingFrom = checkingService.findByAccountNumber(request.getAccountNumberFrom());
    		balanceBeforeMovementFrom = checkingFrom.getBalance();
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.CONFLICT);
    	}
    	if(savingsService.existsCbu(request.getCbuTo())) {
    		savingsTo = savingsService.findByCbu(request.getCbuTo());
    		balanceBeforeMovementTo = savingsTo.getBalance();
    	}
    	else if(checkingService.existsCbu(request.getCbuTo())) {
    		checkingTo = checkingService.findByCbu(request.getCbuTo());
    		balanceBeforeMovementTo = checkingTo.getBalance();
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.CONFLICT);
    	}
    	
    	if(savingsFrom != null && savingsTo != null) {
    		whereFrom = 1;
    	}
    	else if(savingsFrom != null && checkingTo != null) {
    		whereFrom = 2;
    	}
    	else if(checkingFrom != null && savingsTo != null) {
    		whereFrom = 3;
    	}
    	else if(checkingFrom != null && checkingTo != null) {
    		whereFrom = 4;
    	}
    	
    	switch (whereFrom) {
		case 1: //From savings to savings
			canBePerformed = savingsFrom.extract(request.getAmount());
    		if(canBePerformed) {
    			savingsTo.deposit(request.getAmount());
    			return new ResponseEntity<MovementDTO>(movementService.transferToOtherAccounts(request.getAmount(), balanceBeforeMovementFrom, balanceBeforeMovementTo, savingsFrom, savingsTo, checkingFrom, checkingTo, whereFrom, request.getReference()),HttpStatus.OK);
    		}
    		else {
    			return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    		}
		
		case 2: //From savings to checking
			canBePerformed = savingsFrom.extract(request.getAmount());
    		if(canBePerformed) {
    			checkingTo.deposit(request.getAmount());
    			return new ResponseEntity<MovementDTO>(movementService.transferToOtherAccounts(request.getAmount(), balanceBeforeMovementFrom, balanceBeforeMovementTo, savingsFrom, savingsTo, checkingFrom, checkingTo, whereFrom, request.getReference()),HttpStatus.OK);
    		}
    		else {
    			return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    		}
    	
		case 3: //From checking to savings
			canBePerformed = checkingFrom.extract(request.getAmount());
    		if(canBePerformed) {
    			savingsTo.deposit(request.getAmount());
    			return new ResponseEntity<MovementDTO>(movementService.transferToOtherAccounts(request.getAmount(), balanceBeforeMovementFrom, balanceBeforeMovementTo, savingsFrom, savingsTo, checkingFrom, checkingTo, whereFrom, request.getReference()),HttpStatus.OK);
    		}
    		else {
    			return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    		}

		case 4: //From checking to checking
			canBePerformed = checkingFrom.extract(request.getAmount());
    		if(canBePerformed) {
    			checkingTo.deposit(request.getAmount());
    			return new ResponseEntity<MovementDTO>(movementService.transferToOtherAccounts(request.getAmount(), balanceBeforeMovementFrom, balanceBeforeMovementTo, savingsFrom, savingsTo, checkingFrom, checkingTo, whereFrom, request.getReference()),HttpStatus.OK);
    		}
    		else {
    			return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    		}
    		
		default:
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}    
    }
    
    /*
     * 
     * Precondiciones:
     * 			- Debe estar validado que el servicio no haya sido pagado ya (en el buscador).
     * 			- Debe estar validado que el servicio exista (en el buscador).
     * 
     * */
    @PostMapping("/payServiceBill")
    public ResponseEntity<MovementDTO> payServices(@RequestBody ServicePaymentRequest request){
		Savings savingsFrom;
		Checking checkingFrom;
		ServicePayment servicePayment = billService.findServiceByServicePaymentId(request.getIdServicePayment(), request.getVendorId());
		Physical physicalWhoPays;
		Legal legalWhoPays;
		float balanceBeforeMovementFrom;
    	float balanceBeforeMovementTo;
    	boolean canBePerformed;
		Savings savingsTo = null;
		Checking checkingTo = null;
		byte whereTo;

		if(servicePayment.getVendorChecking() != null) {
			checkingTo = servicePayment.getVendorChecking();
			balanceBeforeMovementTo = checkingTo.getBalance();
			checkingTo.deposit(servicePayment.getAmount());
			whereTo = 0;
		}
		else {
			savingsTo = servicePayment.getVendorSavings();
			balanceBeforeMovementTo = savingsTo.getBalance();
			savingsTo.deposit(servicePayment.getAmount());
			whereTo = 1;
		}
		
		if(physicalUserService.existsUser(request.getUsernameFrom())) {
			physicalWhoPays = physicalUserService.findByActiveUsername(request.getUsernameFrom());
			servicePayment.setPhysicalWhoPays(physicalWhoPays);
			if(checkingService.existsAccountNumber(request.getAccountNumberFrom())) {
				checkingFrom = checkingService.findByAccountNumber(request.getAccountNumberFrom());
				balanceBeforeMovementFrom = checkingFrom.getBalance();
				canBePerformed = checkingFrom.extract(servicePayment.getAmount());
				if(canBePerformed) {
					
					return new ResponseEntity<MovementDTO>(movementService.payServices((byte)0, checkingFrom, null, servicePayment, whereTo, checkingTo, savingsTo, balanceBeforeMovementFrom, balanceBeforeMovementTo),HttpStatus.OK);
				}
				else {
					return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT); //Sin saldo.
				}
			}
			else if(savingsService.existsAccountNumber(request.getAccountNumberFrom())) {
				savingsFrom = savingsService.findByAccountNumber(request.getAccountNumberFrom());
				balanceBeforeMovementFrom = savingsFrom.getBalance();
				canBePerformed = savingsFrom.extract(servicePayment.getAmount());
				if(canBePerformed) {
					
					return new ResponseEntity<MovementDTO>(movementService.payServices((byte)1, null, savingsFrom, servicePayment, whereTo, checkingTo, savingsTo, balanceBeforeMovementFrom, balanceBeforeMovementTo),HttpStatus.OK);
				}
				else {
					return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT); //Sin saldo.
				}
			}
			else {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		}
		else if(legalUserService.existsByUsername(request.getUsernameFrom())) {
			legalWhoPays = legalUserService.findByActiveUsername(request.getUsernameFrom());
			servicePayment.setLegalWhoPays(legalWhoPays);
			if(checkingService.existsAccountNumber(request.getAccountNumberFrom())) {
				checkingFrom = checkingService.findByAccountNumber(request.getAccountNumberFrom());
				balanceBeforeMovementFrom = checkingFrom.getBalance();
				canBePerformed = checkingFrom.extract(servicePayment.getAmount());
				if(canBePerformed) {
					
					return new ResponseEntity<MovementDTO>(movementService.payServices((byte)0, checkingFrom, null, servicePayment, whereTo, checkingTo, savingsTo, balanceBeforeMovementFrom, balanceBeforeMovementTo),HttpStatus.OK);
				}
				else {
					return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT); //Sin saldo.
				}
			}
			else if(savingsService.existsAccountNumber(request.getAccountNumberFrom())) {
				savingsFrom = savingsService.findByAccountNumber(request.getAccountNumberFrom());
				balanceBeforeMovementFrom = savingsFrom.getBalance();
				canBePerformed = savingsFrom.extract(servicePayment.getAmount());
				if(canBePerformed) {
					
					return new ResponseEntity<MovementDTO>(movementService.payServices((byte) 1, null, savingsFrom, servicePayment, whereTo, checkingTo, savingsTo, balanceBeforeMovementFrom, balanceBeforeMovementTo),HttpStatus.OK);
				}
				else {
					return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT); //Sin saldo.
				}
			}
			else {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		}
		else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
    }

}


