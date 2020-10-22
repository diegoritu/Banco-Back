package com.banco.api.repository.scheduledTransaction;

import com.banco.api.model.scheduledTransaction.salary.SalaryPayment;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryPaymentRepository extends ScheduledTransactionRepository<SalaryPayment> {

}
