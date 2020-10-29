package com.banco.api.repository.account;

import com.banco.api.model.account.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AccountBaseRepository<T extends Account> extends CrudRepository<T, Integer> {

    public T findByAccountNumberAndActive(String accountNumber, boolean active);

    public T findByCbuAndActive(String cbu, boolean active);
}
