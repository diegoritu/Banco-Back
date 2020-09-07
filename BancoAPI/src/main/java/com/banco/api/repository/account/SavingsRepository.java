package com.banco.api.repository.account;

import com.banco.api.model.account.Savings;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsRepository extends AccountBaseRepository<Savings> {
	   public Savings findByAccountNumber(String accountNumber);

	   public Savings findByCbu(String cbu);
}
