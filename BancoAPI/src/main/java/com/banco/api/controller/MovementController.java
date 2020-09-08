package com.banco.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.api.dto.movement.MovementDTO;
import com.banco.api.dto.movement.request.DepositAndExtractionRequest;
import com.banco.api.dto.movement.request.TransferBetweenOwnAccountsRequest;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.service.account.CheckingService;
import com.banco.api.service.account.SavingsService;
import com.banco.api.service.others.MovementService;


@RestController
@RequestMapping("/movement")
public class MovementController {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private SavingsService savingsService;
    @Autowired
    private CheckingService checkingService;
    @Autowired
    private MovementService movementService;
    
    @PostMapping("/deposit")
    public ResponseEntity<MovementDTO> deposit(@RequestBody DepositAndExtractionRequest request){
    	float balanceBeforeMovement;
    	LOGGER.info("Adding movement: {}", request.toString());
    	if(savingsService.existsAccountNumber(request.getAccountNumberEntryAccount())) {
    		Savings savingsEntry = savingsService.findByAccountNumber(request.getAccountNumberEntryAccount());
    		balanceBeforeMovement = savingsEntry.getBalance();
    		savingsEntry.deposit(request.getAmount());
    		return new ResponseEntity<MovementDTO>(movementService.depositAndExtract(request.getAmount(), balanceBeforeMovement, 0, savingsEntry, null, 0),HttpStatus.OK);
    	}
    	
    	else if(checkingService.existsAccountNumber(request.getAccountNumberEntryAccount())){
    		Checking checkingEntry = checkingService.findByAccountNumber(request.getAccountNumberEntryAccount());
    		balanceBeforeMovement = checkingEntry.getBalance();
    		checkingEntry.deposit(request.getAmount());
    		return new ResponseEntity<MovementDTO>(movementService.depositAndExtract(request.getAmount(), balanceBeforeMovement, 1, null, checkingEntry, 0),HttpStatus.OK);
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
    			return new ResponseEntity<MovementDTO>(movementService.depositAndExtract(request.getAmount(), balanceBeforeMovement, 0, savingsExit, null, 1),HttpStatus.OK);
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
    			return new ResponseEntity<MovementDTO>(movementService.depositAndExtract(request.getAmount(), balanceBeforeMovement, 1, null, checkingExit, 1),HttpStatus.OK);
    		}
    		else {
    			return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    		}
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.CONFLICT);
    	}
    }
    
    @PostMapping("/transferenceBetweenOwnAccounts")
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
    	
    	if(fromSavings == true) {
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
}

