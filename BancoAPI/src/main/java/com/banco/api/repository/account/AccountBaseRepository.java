package com.banco.api.repository.account;

import com.banco.api.model.internal.account.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AccountBaseRepository<T extends Account> extends CrudRepository<T, Integer> {

    public T findByAccountNumber(String accountNumber);

    public T findByCbu(String cbu);
}
