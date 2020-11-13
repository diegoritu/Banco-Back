package com.banco.api.repository;

import com.banco.api.model.ServicePayment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ServiceRepository extends CrudRepository<ServicePayment, Integer> {

	ServicePayment findByServicePaymentId(String servicePaymentId);

	ServicePayment findByServicePaymentIdAndPaid(String servicePaymentId, boolean paid);

	@Query("select s from ServicePayment s where s.automatic = false and s.vendor.vendorId = :vendorId and (:dueDate is null or s.due >= :dueDate)")
	List<ServicePayment> findManualByVendorIdFromDueDate(@Param("vendorId") String vendorId, @Param("dueDate") Date dueDate);

	boolean existsByServicePaymentIdAndVendorIdUser(String servicePaymentId, int vendor);

	boolean existsByServicePaymentIdAndDue(String servicePaymentId, Date due);

	ServicePayment findByVendorIdUserAndServicePaymentIdAndPaid(int id, String servicePaymentId, boolean paid);
}
