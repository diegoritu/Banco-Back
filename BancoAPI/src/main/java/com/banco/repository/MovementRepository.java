package com.banco.repository;

import org.springframework.data.repository.CrudRepository;

import com.banco.api.entities.MovementEntity;
import com.banco.api.model.Movement;

public interface MovementRepository extends CrudRepository<MovementEntity, Integer>{

	MovementEntity toEntity(Movement movement);
	
	Movement toModel(MovementEntity entity);

}
