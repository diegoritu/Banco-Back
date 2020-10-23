package com.banco.api.task;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class ScheduledTransactionTaskExecutor {

    private List<MyTask> tasks;

    //Everyday at 01:00:00
    @Scheduled(cron = "0 0 1 * * ?")
    public void executeTasks() {
        tasks.forEach(MyTask::execute);
    }

    public void addTask(MyTask task) {
        if (CollectionUtils.isEmpty(this.tasks))
            this.tasks = newArrayList();

        this.tasks.add(task);
    }
}
