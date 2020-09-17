package com.banco.api.repository.user;

import com.banco.api.model.user.Legal;

import org.springframework.stereotype.Repository;

@Repository
public interface LegalRepository extends UserBaseRepository<Legal> {

	Legal findByUsernameAndUserTypeNumber(String username, int userTypeNumber);

	Legal findByBusinessName(String businessName);

	Legal findByBusinessNameContainingIgnoreCase(String businessName);

	Legal findByUsernameAndUserTypeNumberAndActive(String username, int userTypeNumber, boolean active);
}
