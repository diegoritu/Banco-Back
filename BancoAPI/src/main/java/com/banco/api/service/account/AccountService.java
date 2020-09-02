package com.banco.api.service.account;


import com.banco.api.dto.account.AccountDTO;
import com.banco.api.dto.account.request.AccountRequest;
import com.banco.api.model.account.Account;

public abstract class AccountService<T extends Account, D extends AccountDTO, R extends AccountRequest>{
    protected abstract D createAccount(R accountRequest);
    
    protected void mapAccount(T account, R accountRequest) {
    	account.setAccountNumber(accountRequest.getAccountNumber());
    	account.setAccountType(accountRequest.getAccountType());
    	account.setAlias(accountRequest.getAlias());
    	account.setBalance(accountRequest.getBalance());
    	account.setCbu(accountRequest.getCbu());
    }
}
