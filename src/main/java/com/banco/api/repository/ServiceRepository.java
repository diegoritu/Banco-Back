package com.banco.api.repository;

import com.banco.api.model.ServicePayment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ServiceRepository extends CrudRepository<ServicePayment, Integer> {
	

	ServicePayment findByServicePaymentId(String servicePaymentId);

	ServicePayment findByServicePaymentIdAndPaid(String servicePaymentId, boolean paid);

	boolean existsByServicePaymentIdAndVendorIdUser(String servicePaymentId, int vendor);

	boolean existsByServicePaymentIdAndDue(String servicePaymentId, Date due);
}
