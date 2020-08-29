package com.banco.api.dao.repository;

import org.springframework.data.repository.CrudRepository;

import com.banco.api.model.Movement;
import org.springframework.stereotype.Repository;

public interface MovementRepository extends CrudRepository<Movement, Integer>{

}
