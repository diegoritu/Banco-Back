package com.banco.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.banco.api.model.internal.Movement;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends CrudRepository<Movement, Integer>{

}
