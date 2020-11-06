package com.banco.api.task;

import com.banco.api.model.scheduledTransaction.ScheduledTransactionStatus;
import com.banco.api.model.scheduledTransaction.billService.ScheduledCollectService;
import com.banco.api.repository.scheduledTransaction.ScheduledCollectServiceRepository;
import com.banco.api.service.billService.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Component
public class CollectServiceTask implements MyTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollectServiceTask.class);

    @Autowired
    private ScheduledCollectServiceRepository scheduledCollectServiceRepository;
    @Autowired
    private BillService billService;
    @Autowired
    private ScheduledTransactionTaskExecutor scheduledTransactionTaskExecutor;

    @PostConstruct
    private void postConstruct() {
        scheduledTransactionTaskExecutor.addTask(this);
    }

    @Override
    public void execute() {
        LOGGER.info("Executing Collect Service Task");
        List<ScheduledCollectService> collectServices = scheduledCollectServiceRepository
                .findAllByStatusAndScheduledDateAndServicePayment_Paid(ScheduledTransactionStatus.PENDING.getValue(), new Date(), false);

        LOGGER.info("Pending service collections scheduled for today: {}", collectServices != null ? collectServices.size() : 0);

        if (collectServices != null) {
            collectServices.forEach(s -> billService.collectService(s));
        }
    }
}
