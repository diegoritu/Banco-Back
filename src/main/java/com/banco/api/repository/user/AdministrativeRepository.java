package com.banco.api.repository.user;

import com.banco.api.model.user.Administrative;

import org.springframework.stereotype.Repository;

@Repository
public interface AdministrativeRepository extends UserBaseRepository<Administrative> {
	
	Administrative findByDni(String dni);

	Administrative findByLastName(String lastName);

	Administrative findByUsernameAndUserTypeNumberAndActive(String username, int userTypeNumber, boolean active);

	boolean existsByDni(String dni);
}
