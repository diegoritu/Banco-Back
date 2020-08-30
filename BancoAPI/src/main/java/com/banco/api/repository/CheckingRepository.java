package com.banco.api.repository;

import com.banco.api.model.account.Checking;
import org.springframework.data.repository.CrudRepository;

public interface CheckingRepository extends CrudRepository<Checking, Integer>{

}
