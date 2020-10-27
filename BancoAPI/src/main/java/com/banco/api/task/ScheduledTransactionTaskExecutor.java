package com.banco.api.task;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class ScheduledTransactionTaskExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTransactionTaskExecutor.class);

    private List<MyTask> tasks;

    //Everyday at 01:00:00
    @Scheduled(cron = "0 0 1 * * ?")
    public void executeTasks() {
        LOGGER.info("Executing scheduled transaction tasks process started");
        if (CollectionUtils.isNotEmpty(tasks)) {
            tasks.forEach(MyTask::execute);
        } else {
            LOGGER.info("No tasks to execute");
        }
    }

    public void addTask(MyTask task) {
        if (CollectionUtils.isEmpty(this.tasks))
            this.tasks = newArrayList();

        this.tasks.add(task);
    }
}
