package com.banco.api.repository.account;

import com.banco.api.model.account.Savings;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsRepository extends AccountBaseRepository<Savings> {
	   public Savings findByAccountNumberAndActive(String accountNumber, boolean active);

	   public Savings findByCbuAndActive(String cbu, boolean active);
}
