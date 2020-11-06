package com.banco.api.repository.scheduledTransaction;

import com.banco.api.model.scheduledTransaction.ScheduledTransaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

@NoRepositoryBean
public interface ScheduledTransactionRepository<T extends ScheduledTransaction> extends CrudRepository<T, Integer> {

    List<T> findAllByStatus(int status);

    List<T> findAllByStatusAndScheduledDate(int status, Date date);

    @Query("select s from ScheduledTransaction s where s.status = :status and s.scheduledDate >= :scheduledDate")
    List<T> findAllByStatusFromScheduledDate(@Param("status") int status, @Param("scheduledDate") Date scheduledDate);
}
