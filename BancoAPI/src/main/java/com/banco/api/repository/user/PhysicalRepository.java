package com.banco.api.repository.user;

import com.banco.api.model.user.Physical;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhysicalRepository extends UserBaseRepository<Physical> {

	List<Physical> findByDni(String dni);

	List<Physical> findByFirstNameContainingIgnoreCase(String firstName);

	List<Physical> findByLastNameContainingIgnoreCase(String lastName);

	Physical findByUsernameAndUserTypeNumberAndActive(String username, int userTypeNumber, boolean active);
}
