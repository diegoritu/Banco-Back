package com.banco.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.banco.api.model.Movement;

public interface MovementRepository extends CrudRepository<Movement, Integer>{

}
