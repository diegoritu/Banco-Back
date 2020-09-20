package com.banco.api.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import com.banco.api.model.Movement;

import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends CrudRepository<Movement, Integer>{

	Collection<Movement> findByChEntryAccountIdAccountOrChExitAccountIdAccountOrderByDayAndHourAsc(int idEntry, int idExit);
	
	Collection<Movement> findBySaEntryAccountIdAccountOrSaExitAccountIdAccountOrderByDayAndHourAsc(int idEntry, int idExit);

}
