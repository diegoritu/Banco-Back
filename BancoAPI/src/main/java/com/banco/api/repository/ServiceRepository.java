package com.banco.api.repository;

import com.banco.api.model.ServicePayment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends CrudRepository<ServicePayment, Integer> {
	

	ServicePayment findByIdServicePayment(String idServicePayment);
	
	ServicePayment findByIdServicePaymentAndVendorId(String idServicePayment, String vendorId);


}
