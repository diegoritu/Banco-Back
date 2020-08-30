package com.banco.api.repository.account;

import com.banco.api.model.account.Checking;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingRepository extends AccountBaseRepository<Checking> {
}
