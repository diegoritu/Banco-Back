package com.banco.api.repository.account;

import com.banco.api.model.internal.account.Savings;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsRepository extends AccountBaseRepository<Savings> {
}
