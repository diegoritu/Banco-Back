package com.banco.api.service.account;


import com.banco.api.dto.account.AccountDTO;
import com.banco.api.dto.account.request.AccountRequest;
import com.banco.api.model.account.Account;
import com.banco.api.model.account.Checking;

public abstract class AccountService<T extends Account, D extends AccountDTO, R extends AccountRequest>{
    protected abstract D createAccount(String username);
    
    protected void mapAccount(T account, R accountRequest) {
    	account.setAccountNumber(accountRequest.getAccountNumber());
    	account.setAccountType(accountRequest.getAccountType());
    	account.setActive(accountRequest.isActive());
    	account.setBalance(accountRequest.getBalance());
    	account.setCbu(accountRequest.getCbu());
    }

    
    protected abstract T createAccount();

}
