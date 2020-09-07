package com.banco.api.service.account;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.api.dto.account.CheckingDTO;
import com.banco.api.dto.account.request.CheckingRequest;
import com.banco.api.model.account.Checking;
import com.banco.api.repository.account.CheckingRepository;

@Service
public class CheckingService extends AccountService<Checking, CheckingDTO, CheckingRequest> {

    @Autowired
    CheckingRepository checkingRepository;

    @Override
    public Checking createAccount() {
        Checking account = new Checking(0, 0, 0);
        
    	while(existsAccountNumber(account.getAccountNumber())) {
            account = new Checking(0, 0, 0);
        }
    	
        Checking result = checkingRepository.save(account);
        return result;
    }
    
    public boolean existsAccountNumber(String accountNumber) {
    	boolean result;
    	
    	if(checkingRepository.findByAccountNumber(accountNumber) == null) {
    		result = false;
    	}
    	else {
    		result = true;
    	}
    	return result;
    }

	@Override
	protected CheckingDTO createAccount(String username) {
		// TODO Auto-generated method stub
		return null;
	}

    
}