package com.banco.api.service.account;


import com.banco.api.dto.account.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.api.dto.account.CheckingDTO;
import com.banco.api.model.account.Checking;
import com.banco.api.repository.account.CheckingRepository;

@Service
public class CheckingService extends AccountService<Checking, CheckingDTO> {

    @Autowired
    CheckingRepository checkingRepository;

    public Checking createAccount(Float maxOverdraft) {
        Checking account = new Checking(AccountType.CHECKING.getValue(), maxOverdraft);

    	while(existsAccountNumber(account.getAccountNumber())) {
            account = new Checking(AccountType.CHECKING.getValue(), maxOverdraft);
        }

        Checking result = checkingRepository.save(account);
        return result;
    }
    
    public boolean existsAccountNumber(String accountNumber) {
    	return findByAccountNumber(accountNumber) != null;
    }
    
    public Checking findByAccountNumber(String accountNumber) {
    	Checking result = checkingRepository.findByAccountNumberAndActive(accountNumber, true);
    	return result;
    }
    
    public boolean existsCbu(String cbu) {
    	return findByCbu(cbu) != null;
    }
    
    public Checking findByCbu(String cbu) {
    	Checking result = checkingRepository.findByCbuAndActive(cbu, true);
    	return result;
    }

	public Checking closeAccount(Checking checking) {
		
		if(Float.compare(checking.getBalance(), 0) == 0)
		{
			checking.setActive(false);
			Checking result = checkingRepository.save(checking);
			return result;
		}
		else
		{
			return checking;
		}
	}
	
    public Checking update(Checking checking) {

		Checking result = checkingRepository.save(checking);
		return result;
     }

}