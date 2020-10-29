package com.banco.api.task;

import com.banco.api.model.scheduledTransaction.ScheduledTransactionStatus;
import com.banco.api.model.scheduledTransaction.salary.SalaryPayment;
import com.banco.api.repository.scheduledTransaction.SalaryPaymentRepository;
import com.banco.api.service.SalaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class SalaryPaymentTask implements MyTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalaryPaymentTask.class);

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
        LOGGER.info("Executing Salary Payment Task");
        List<SalaryPayment> salaryPayments = salaryPaymentRepository
                .findAllByStatusAndScheduledDate(ScheduledTransactionStatus.PENDING.getValue(), new Date());

        LOGGER.info("Pending salary payments scheduled for today: {}", salaryPayments != null ? salaryPayments.size() : 0);

        ofNullable(salaryPayments).ifPresent(payments ->
                payments.forEach(payment -> salaryService.paySalary(payment)));
    }
}
