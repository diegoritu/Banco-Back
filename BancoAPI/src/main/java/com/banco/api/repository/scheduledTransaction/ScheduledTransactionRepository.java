package com.banco.api.repository.scheduledTransaction;

import com.banco.api.model.scheduledTransaction.ScheduledTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ScheduledTransactionRepository<T extends ScheduledTransaction> extends CrudRepository<T, Integer> {

}
