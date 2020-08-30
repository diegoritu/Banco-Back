package com.banco.api.repository.user;

import com.banco.api.model.user.Administrative;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdministrativeRepository extends UserBaseRepository<Administrative> {

}
