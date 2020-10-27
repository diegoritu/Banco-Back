package com.banco.api.repository.scheduledTransaction;

import com.banco.api.model.scheduledTransaction.ScheduledTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Date;
import java.util.List;

@NoRepositoryBean
public interface ScheduledTransactionRepository<T extends ScheduledTransaction> extends CrudRepository<T, Integer> {

    List<T> findAllByScheduledDate(Date scheduledDate);

    List<T> findAllByStatus(int status);

    List<T> findAllByStatusAndScheduledDate(int status, Date date);
}
