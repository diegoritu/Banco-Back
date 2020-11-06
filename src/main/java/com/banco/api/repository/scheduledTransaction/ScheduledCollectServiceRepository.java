package com.banco.api.repository.scheduledTransaction;

import com.banco.api.model.scheduledTransaction.billService.ScheduledCollectService;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduledCollectServiceRepository extends ScheduledTransactionRepository<ScheduledCollectService> {

    List<ScheduledCollectService> findAllByStatusAndScheduledDateAndServicePayment_Paid(int status, Date scheduledDate, boolean paid);

}
