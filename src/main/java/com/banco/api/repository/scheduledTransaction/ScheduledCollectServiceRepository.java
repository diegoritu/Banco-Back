package com.banco.api.repository.scheduledTransaction;

import com.banco.api.model.scheduledTransaction.billService.ScheduledCollectService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduledCollectServiceRepository extends ScheduledTransactionRepository<ScheduledCollectService> {

    List<ScheduledCollectService> findAllByStatusAndScheduledDateAndServicePayment_Paid(int status, Date scheduledDate, boolean paid);

    @Query("select s from ScheduledCollectService s where s.servicePayment.vendor.vendorId = :vendorId and (:scheduledDate is null or s.scheduledDate >= :scheduledDate)")
    List<ScheduledCollectService> findAllByVendorIdFromScheduledDate(@Param("vendorId") String vendorId, @Param("scheduledDate") Date scheduledDate);

}
