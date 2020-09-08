package com.banco.api.service.others;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.api.dto.movement.MovementDTO;
import com.banco.api.dto.movement.request.DepositAndExtractionRequest;
import com.banco.api.model.Movement;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.repository.MovementRepository;

@Service
public class MovementService {
	@Autowired
	MovementRepository movementRepository;
	
	public MovementDTO depositAndExtract(float amount, float balanceBeforeMovement, int accountType, Savings savings, Checking checking, int movementType) {
		MovementDTO movementDTO = new MovementDTO();
		Movement result = new Movement();
        Date now = new Date();
		movementDTO.setMovementType(movementType);
		movementDTO.setDayAndHour(now.toString());
		movementDTO.setAmount(amount);
		
		result.setMovementType(movementType);
		result.setDayAndHour(now);
		result.setAmount(amount);
		if(movementType == 0) {
			if(accountType == 0) {
				movementDTO.setSaEntryAccount(savings.toView());
				movementDTO.setEntryBalanceBeforeMovement(balanceBeforeMovement);
				result.setSaEntryAccount(savings);
				result.setEntryBalanceBeforeMovement(balanceBeforeMovement);				
			}
			else {
				movementDTO.setChEntryAccount(checking.toView());
				movementDTO.setEntryBalanceBeforeMovement(balanceBeforeMovement);
				result.setChEntryAccount(checking);
				result.setEntryBalanceBeforeMovement(balanceBeforeMovement);
			}
		}
		else {
			if(accountType == 0) {
				movementDTO.setSaExitAccount(savings.toView());
				movementDTO.setExitBalanceBeforeMovement(balanceBeforeMovement);
				result.setSaExitAccount(savings);
				result.setExitBalanceBeforeMovement(balanceBeforeMovement);				
			}
			else {
				movementDTO.setChExitAccount(checking.toView());
				movementDTO.setExitBalanceBeforeMovement(balanceBeforeMovement);
				result.setChExitAccount(checking);
				result.setExitBalanceBeforeMovement(balanceBeforeMovement);
			}
		}
		movementRepository.save(result);		
		return movementDTO;
	}
	
	public MovementDTO transferBetweenOwnAccounts(float amount, float balanceBeforeMovementFrom, float balanceBeforeMovementTo, Savings savingsFrom, Savings savingsTo, Checking checkingFrom, Checking checkingTo, boolean accountTypes) {
		MovementDTO movementDTO = new MovementDTO();
		Movement result = new Movement();
		Date now = new Date();
		
		movementDTO.setDayAndHour(now.toString());
		movementDTO.setAmount(amount);
		movementDTO.setEntryBalanceBeforeMovement(balanceBeforeMovementFrom);
		movementDTO.setExitBalanceBeforeMovement(balanceBeforeMovementTo);
		movementDTO.setMovementType(5);
		
		result.setMovementType(5);
		result.setDayAndHour(now);
		result.setAmount(amount);
		result.setEntryBalanceBeforeMovement(balanceBeforeMovementFrom);
		result.setExitBalanceBeforeMovement(balanceBeforeMovementTo);
		
		if(accountTypes) { //From savings to checking
			movementDTO.setSaExitAccount(savingsFrom.toView());
			movementDTO.setChEntryAccount(checkingTo.toView());
			result.setChEntryAccount(checkingTo);
			result.setSaExitAccount(savingsFrom);
		}
		else { //From checking to savings
			movementDTO.setChExitAccount(checkingFrom.toView());
			movementDTO.setSaEntryAccount(savingsTo.toView());
			result.setChExitAccount(checkingFrom);
			result.setSaEntryAccount(savingsTo);
		}
		
		movementRepository.save(result);		
		return movementDTO;
	}
	
}
