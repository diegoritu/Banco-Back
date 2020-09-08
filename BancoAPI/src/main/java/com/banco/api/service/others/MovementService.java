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
	
	public MovementDTO depositAndExtract(DepositAndExtractionRequest request, float balanceBeforeMovement, int accountType, Savings savingsEntry, Checking checkingEntry, int movementType) {
		MovementDTO movementDTO = new MovementDTO();
		Movement result = new Movement();
        Date now = new Date();
		movementDTO.setMovementType(movementType);
		movementDTO.setDayAndHour(now.toString());
		movementDTO.setAmount(request.getAmount());
		movementDTO.setEntryBalanceBeforeMovement(balanceBeforeMovement);
		
		result.setMovementType(movementType);
		result.setDayAndHour(now);
		result.setAmount(request.getAmount());
		result.setEntryBalanceBeforeMovement(balanceBeforeMovement);
		
		if(accountType == 0) {
			movementDTO.setSaEntryAccount(savingsEntry.toView());
			result.setSaEntryAccount(savingsEntry);
		}
		else {
			movementDTO.setChEntryAccount(checkingEntry.toView());
			result.setChEntryAccount(checkingEntry);
		}
		movementRepository.save(result);		
		return movementDTO;
	}
	
}
