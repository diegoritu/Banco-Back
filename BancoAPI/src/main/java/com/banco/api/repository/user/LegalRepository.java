package com.banco.api.repository.user;

import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.user.Legal;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LegalRepository extends UserBaseRepository<Legal> {

	Legal findByUsernameAndUserTypeNumber(String username, int userTypeNumber);

	List<Legal> findByActiveTrueAndBusinessNameContainingIgnoreCase(String businessName);

	Legal findByUsernameAndUserTypeNumberAndActive(String username, int userTypeNumber, boolean active);

	Legal findByVendorId(String vendorId);

	boolean existsByVendorId(String vendorId);

	List<Legal> findByActiveTrue();
	
	Legal findByChecking(Checking checkingAccount);
	
	Legal findBySavings(Savings savingsAccount);
}
