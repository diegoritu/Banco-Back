package com.banco.api.dao.repository;

import com.banco.api.model.Savings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface SavingsRepository extends CrudRepository<Savings, Integer> {

}
