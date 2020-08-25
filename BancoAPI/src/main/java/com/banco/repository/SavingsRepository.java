package com.banco.repository;

import org.springframework.data.repository.CrudRepository;

import com.banco.api.entities.SavingsEntity;
import com.banco.api.model.Savings;

public interface SavingsRepository extends CrudRepository<SavingsEntity, Integer> {
	
	SavingsEntity toEntity(Savings movement);
	
	Savings toModel(SavingsEntity entity);

}
