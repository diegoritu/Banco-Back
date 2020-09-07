package com.banco.api.service.others;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.api.dto.account.SavingsDTO;
import com.banco.api.dto.others.MovementDTO;
import com.banco.api.dto.others.request.MovementRequest;
import com.banco.api.model.Movement;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.repository.MovementRepository;

@Service
public class MovementService {
	@Autowired
	MovementRepository movementRepository;
	
	public MovementDTO deposit(MovementRequest request, float balanceBeforeMovement, int accountType, Savings savingsEntry, Checking checkingEntry) {
		MovementDTO movementDTO = new MovementDTO();
		Movement result = new Movement();
        Date now = new Date();
		movementDTO.setMovementType(0);
		movementDTO.setDayAndHour(now.toString());
		movementDTO.setConcept(request.getConcept());
		movementDTO.setAmount(request.getAmount());
		movementDTO.setEntryBalanceBeforeMovement(balanceBeforeMovement);
		
		result.setMovementType(0);
		result.setDayAndHour(now);
		result.setConcept(request.getConcept());
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
