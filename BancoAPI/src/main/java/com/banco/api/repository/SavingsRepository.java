package com.banco.api.repository;

import com.banco.api.model.account.Savings;
import org.springframework.data.repository.CrudRepository;

public interface SavingsRepository extends CrudRepository<Savings, Integer> {

}
