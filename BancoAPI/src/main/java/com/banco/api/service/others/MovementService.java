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
import com.banco.api.service.account.CheckingService;
import com.banco.api.service.account.SavingsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
public class MovementService {
	@Autowired
	MovementRepository movementRepository;
	@Autowired
	CheckingService checkingService;
	@Autowired
	SavingsService savingsService;
	
	
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
		movementDTO.setEntryBalanceBeforeMovement(balanceBeforeMovementTo);
		movementDTO.setExitBalanceBeforeMovement(balanceBeforeMovementFrom);
		movementDTO.setMovementType(5);
		
		result.setMovementType(5);
		result.setDayAndHour(now);
		result.setAmount(amount);
		result.setEntryBalanceBeforeMovement(balanceBeforeMovementTo);
		result.setExitBalanceBeforeMovement(balanceBeforeMovementFrom);
		
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
		movementDTO.setEntryBalanceBeforeMovement(balanceBeforeMovementTo);
		movementDTO.setExitBalanceBeforeMovement(balanceBeforeMovementFrom);
		movementDTO.setMovementType(6);
		movementDTO.setReference(reference);
		
		result.setReference(reference);
		result.setMovementType(6);
		result.setDayAndHour(now);
		result.setAmount(amount);
		result.setEntryBalanceBeforeMovement(balanceBeforeMovementTo);
		result.setExitBalanceBeforeMovement(balanceBeforeMovementFrom);
		
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

	public MovementDTO payServices(byte whereFrom, Checking checkingFrom, Savings savingsFrom, ServicePayment servicePayment, Physical physicalWhoPays, Legal legalWhoPays, byte whereTo, Checking checkingTo, Savings savingsTo, float balanceBeforeMovementFrom, float balanceBeforeMovementTo) {
		MovementDTO movementDTO = new MovementDTO();
		Movement result = new Movement();
		Date now = new Date();
		
		servicePayment.setPaid(true);
		
		movementDTO.setDayAndHour(now.toString());
		movementDTO.setEntryBalanceBeforeMovement(balanceBeforeMovementTo);
		movementDTO.setExitBalanceBeforeMovement(balanceBeforeMovementFrom);
		movementDTO.setAmount(servicePayment.getAmount());
		movementDTO.setMovementType(4);
		movementDTO.setService(servicePayment.toView());
		
		result.setDayAndHour(now);
		result.setEntryBalanceBeforeMovement(balanceBeforeMovementTo);
		result.setExitBalanceBeforeMovement(balanceBeforeMovementFrom);
		result.setAmount(servicePayment.getAmount());
		result.setMovementType(4);
		result.setService(servicePayment);
		
		if(whereFrom == 0) {
			result.setChExitAccount(checkingFrom);
			movementDTO.setChExitAccount(checkingFrom.toView());
		}
		else if(whereFrom == 1) {
			result.setSaExitAccount(savingsFrom);
			movementDTO.setSaExitAccount(savingsFrom.toView());
		}
		if(whereTo == 0) {
			result.setChEntryAccount(servicePayment.getVendorChecking());
			movementDTO.setChEntryAccount(servicePayment.getVendorChecking().toView());
		}
		else {
			result.setSaEntryAccount(servicePayment.getVendorSavings());
			movementDTO.setSaEntryAccount(servicePayment.getVendorSavings().toView());
		}
		
		movementRepository.save(result);
		
		return movementDTO;
	}

	public Collection<MovementDTO> getMovements(String accountNumber, byte accountType) {
		Collection<MovementDTO> result = new ArrayList<MovementDTO>();
		Collection<Movement> movements = new ArrayList<Movement>();
		Checking checking;
		Savings savings;
		if(accountType == 0) {
			checking = checkingService.findByAccountNumber(accountNumber);
			movements = movementRepository.findByChEntryAccountIdAccountOrChExitAccountIdAccountOrderByDayAndHourDesc(checking.getIdAccount(), checking.getIdAccount());
		}
		else {
			savings = savingsService.findByAccountNumber(accountNumber);
			movements = movementRepository.findBySaEntryAccountIdAccountOrSaExitAccountIdAccountOrderByDayAndHourDesc(savings.getIdAccount(),savings.getIdAccount());
		}
		for(Movement m : movements) {
			result.add(m.toView());
		}
		
		return result;
	}

	public MovementDTO getMovementById(int id) {
		return movementRepository.findByIdMovement(id).toView();
	}

	
}
