package com.banco.api.dao.repository;

import com.banco.api.dao.entity.CheckingEntity;
import com.banco.api.model.Checking;
import org.springframework.data.repository.CrudRepository;

public interface CheckingRepository extends CrudRepository<CheckingEntity, Integer>{

	CheckingEntity toEntity(Checking checking);
	
    Checking toModel(CheckingEntity entity);

}
