package com.banco.repository;

import org.springframework.data.repository.CrudRepository;

import com.banco.api.entities.CheckingEntity;
import com.banco.api.model.Checking;


public interface CheckingRepository extends CrudRepository<CheckingEntity, Integer>{

	CheckingEntity toEntity(Checking checking);
	
    Checking toModel(CheckingEntity entity);


}
