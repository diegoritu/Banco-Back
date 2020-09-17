package com.banco.api.repository.user;

import com.banco.api.model.user.Legal;
import com.banco.api.model.user.Physical;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicalRepository extends UserBaseRepository<Physical> {

	Physical findByDni(String dni);

	Physical findByFirstNameContainingIgnoreCase(String firstName);

	Physical findByLastNameContainingIgnoreCase(String lastName);

	Physical findByUsernameAndUserTypeNumberAndActive(String username, int userTypeNumber, boolean active);
}
