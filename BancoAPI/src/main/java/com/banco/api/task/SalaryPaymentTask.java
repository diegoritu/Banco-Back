package com.banco.api.task;

import com.banco.api.model.scheduledTransaction.ScheduledTransactionStatus;
import com.banco.api.model.scheduledTransaction.salary.SalaryPayment;
import com.banco.api.repository.scheduledTransaction.SalaryPaymentRepository;
import com.banco.api.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class SalaryPaymentTask implements MyTask {

    @Autowired
    private SalaryService salaryService;
    @Autowired
    private SalaryPaymentRepository salaryPaymentRepository;
    @Autowired
    private ScheduledTransactionTaskExecutor scheduledTransactionTaskExecutor;

    @PostConstruct
    private void postConstruct() {
        scheduledTransactionTaskExecutor.addTask(this);
    }

    @Override
    public void execute() {
        List<SalaryPayment> salaryPayments = salaryPaymentRepository
                .findAllByScheduledDateBeforeAndStatus(new Date(), ScheduledTransactionStatus.PENDING.getValue());

        ofNullable(salaryPayments).ifPresent(payments ->
                payments.forEach(payment -> salaryService.paySalary(payment)));
    }
}
