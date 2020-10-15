package com.banco.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.banco.api.model.DebitCard;

public interface DebitCardRepository extends CrudRepository<DebitCard, Integer>{
	
	boolean existsByNumberAndSecurityCode(String number, String securityCode);
	
	boolean existsByNumber(String number);
	
	DebitCard findByNumberAndSecurityCode(String number, String securityCode);
	
}
