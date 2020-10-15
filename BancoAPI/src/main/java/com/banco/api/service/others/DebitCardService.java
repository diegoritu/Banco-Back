package com.banco.api.service.others;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.api.model.DebitCard;
import com.banco.api.model.account.Savings;
import com.banco.api.repository.DebitCardRepository;
import com.banco.api.service.account.SavingsService;

@Service
public class DebitCardService {
	@Autowired
	private DebitCardRepository debitCardRepository;
	@Autowired
	private SavingsService savingsService;
	
	public boolean existsDebitCard(String number, String securityCode) {
		return debitCardRepository.existsByNumberAndSecurityCode(number, securityCode);
	}
	
	public boolean existsNumber(String number) {
		return debitCardRepository.existsByNumber(number);
	}
	
	public DebitCard findByNumberAndSecurityCode(String number, String securityCode) {
		return debitCardRepository.findByNumberAndSecurityCode(number, securityCode);
	}
	
	public DebitCard createDebitCard(String accountNumber) {
		Savings account = savingsService.findByAccountNumber(accountNumber);
		DebitCard dC = new DebitCard(account);
		
		while(existsNumber(dC.getNumber())) {
			dC = new DebitCard(account);
		}
		
		DebitCard result = debitCardRepository.save(dC);
		
		return result;
	}
}
