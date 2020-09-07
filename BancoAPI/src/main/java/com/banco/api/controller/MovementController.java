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

import com.banco.api.dto.others.MovementDTO;
import com.banco.api.dto.others.request.MovementRequest;
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
    public ResponseEntity<MovementDTO> deposit(@RequestBody MovementRequest request){
    	float balanceBeforeMovement;
    	LOGGER.info("Adding movement: {}", request.toString());
    	if(savingsService.existsAccountNumber(request.getAccountNumberEntryAccount())) {
    		Savings savingsEntry = savingsService.findByAccountNumber(request.getAccountNumberEntryAccount());
    		balanceBeforeMovement = savingsEntry.getBalance();
    		savingsEntry.deposit(request.getAmount());
    		return new ResponseEntity<MovementDTO>(movementService.deposit(request, balanceBeforeMovement, 0, savingsEntry, null),HttpStatus.CREATED);
    	}
    	
    	else if(checkingService.existsAccountNumber(request.getAccountNumberEntryAccount())){
    		Checking checkingEntry = checkingService.findByAccountNumber(request.getAccountNumberEntryAccount());
    		balanceBeforeMovement = checkingEntry.getBalance();
    		checkingEntry.deposit(request.getAmount());
    		return new ResponseEntity<MovementDTO>(movementService.deposit(request, balanceBeforeMovement, 1, null, checkingEntry),HttpStatus.CREATED);
    	}
    	
    	else {
    		return new ResponseEntity<>(HttpStatus.CONFLICT);
    	}
    }
}
