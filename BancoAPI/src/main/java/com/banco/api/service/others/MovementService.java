package com.banco.api.service.others;

import com.banco.api.dto.movement.MovementDTO;
import com.banco.api.dto.movement.MovementType;
import com.banco.api.model.Movement;
import com.banco.api.model.ServicePayment;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.user.Legal;
import com.banco.api.model.user.Physical;
import com.banco.api.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MovementService {
	@Autowired
	MovementRepository movementRepository;
	
	public MovementDTO depositAndExtract(float amount, float balanceBeforeMovement, int accountType, Savings savings, Checking checking, MovementType movementType) {
		MovementDTO movementDTO = new MovementDTO();
		Movement result = new Movement();
        Date now = new Date();
		movementDTO.setMovementType(movementType.getValue());
		movementDTO.setDayAndHour(now.toString());
		movementDTO.setAmount(amount);
		
		result.setMovementType(movementType.getValue());
		result.setDayAndHour(now);
		result.setAmount(amount);
		if(MovementType.DEPOSIT.equals(movementType) || MovementType.INTERESTS.equals(movementType)) {
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
	
	public MovementDTO transferToOtherAccounts(float amount, float balanceBeforeMovementFrom, float balanceBeforeMovementTo, Savings savingsFrom, Savings savingsTo, Checking checkingFrom, Checking checkingTo, int whereFrom, String reference) {
		MovementDTO movementDTO = new MovementDTO();
		Movement result = new Movement();
		Date now = new Date();
		
		movementDTO.setDayAndHour(now.toString());
		movementDTO.setAmount(amount);
		movementDTO.setEntryBalanceBeforeMovement(balanceBeforeMovementFrom);
		movementDTO.setExitBalanceBeforeMovement(balanceBeforeMovementTo);
		movementDTO.setMovementType(6);
		movementDTO.setReference(reference);
		
		result.setReference(reference);
		result.setMovementType(6);
		result.setDayAndHour(now);
		result.setAmount(amount);
		result.setEntryBalanceBeforeMovement(balanceBeforeMovementFrom);
		result.setExitBalanceBeforeMovement(balanceBeforeMovementTo);
		
		switch (whereFrom) {
		case 1: //From savings to savings
			movementDTO.setSaExitAccount(savingsFrom.toView());
			movementDTO.setSaEntryAccount(savingsTo.toView());
			result.setSaEntryAccount(savingsTo);
			result.setSaExitAccount(savingsFrom);
			break;
		
		case 2: //From savings to checking
			movementDTO.setSaExitAccount(savingsFrom.toView());
			movementDTO.setChEntryAccount(checkingTo.toView());
			result.setSaExitAccount(savingsFrom);
			result.setChEntryAccount(checkingTo);
			break;
		
		case 3: //From checking to savings
			movementDTO.setChExitAccount(checkingFrom.toView());
			movementDTO.setSaEntryAccount(savingsTo.toView());
			result.setChExitAccount(checkingFrom);
			result.setSaEntryAccount(savingsTo);
			break;
		
		case 4: //From checking to checking
			movementDTO.setChExitAccount(checkingFrom.toView());
			movementDTO.setChEntryAccount(checkingTo.toView());
			result.setChExitAccount(checkingFrom);
			result.setChEntryAccount(checkingTo);
			break;
			
		default:
			break;
		}
		
		movementRepository.save(result);		
		return movementDTO;
	}

	public MovementDTO payServices(byte whereFrom,byte whoFrom, Checking checkingFrom, Savings savingsFrom, ServicePayment servicePayment, Physical physicalWhoPays, Legal legalWhoPays, byte whereTo, Checking checkingTo, Savings savingsTo, float balanceBeforeMovementFrom, float balanceBeforeMovementTo) {
		MovementDTO movementDTO = new MovementDTO();
		Date now = new Date();
		
		movementDTO.setDayAndHour(now.toString());
		movementDTO.setEntryBalanceBeforeMovement(balanceBeforeMovementTo);
		movementDTO.setExitBalanceBeforeMovement(balanceBeforeMovementFrom);
		movementDTO.setAmount(servicePayment.getAmount());
		movementDTO.setMovementType(4);

		
		if(whoFrom == 0) {
			servicePayment.setPhysicalWhoPays(physicalWhoPays);
		}
		else if (whoFrom == 1) {
			servicePayment.setLegalWhoPays(legalWhoPays);
		}
		if(whereFrom == 0) {
			movementDTO.setChExitAccount(checkingFrom.toView());
		}
		else if(whereFrom == 1) {
			movementDTO.setSaExitAccount(savingsFrom.toView());
		}
		if(whereTo == 0) {
			movementDTO.setChEntryAccount(servicePayment.getVendorChecking().toView());
		}
		else {
			movementDTO.setSaEntryAccount(servicePayment.getVendorSavings().toView());
		}
		return null;
	}
	
}
