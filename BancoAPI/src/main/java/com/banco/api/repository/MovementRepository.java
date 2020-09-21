package com.banco.api.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import com.banco.api.model.Movement;

import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends CrudRepository<Movement, Integer>{

	Collection<Movement> findByChEntryAccountIdAccountOrChExitAccountIdAccountOrderByDayAndHourDesc(int idEntry, int idExit);
	
	Collection<Movement> findBySaEntryAccountIdAccountOrSaExitAccountIdAccountOrderByDayAndHourDesc(int idEntry, int idExit);
	
	Movement findByIdMovement(int id);

}
