package com.banco.api.repository.user;

import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.user.Physical;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhysicalRepository extends UserBaseRepository<Physical> {

	List<Physical> findByActiveTrueAndDni(String dni);

	List<Physical> findByActiveTrueAndFirstNameContainingIgnoreCase(String firstName);

	List<Physical> findByActiveTrueAndLastNameContainingIgnoreCase(String lastName);

	Physical findByUsernameAndUserTypeNumberAndActive(String username, int userTypeNumber, boolean active);

	boolean existsByDni(String dni);

	boolean existsByActiveTrueAndChecking(Checking checking);

	boolean existsByActiveTrueAndSavings(Savings savings);
}
