package com.banco.api.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.api.dto.account.SavingsDTO;
import com.banco.api.dto.account.request.SavingsRequest;
import com.banco.api.model.account.Savings;
import com.banco.api.repository.account.SavingsRepository;


@Service
public class SavingsService extends AccountService<Savings, SavingsDTO, SavingsRequest> {

    @Autowired
    SavingsRepository savingsRepository;

    @Override
    public Savings createAccount() {
        Savings account = new Savings(0, 0);
        
        Savings result = savingsRepository.save(account);
        return result;
    }

	@Override
	protected SavingsDTO createAccount(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}

    