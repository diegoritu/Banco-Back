package com.banco.api.repository.user;

import com.banco.api.model.user.Physical;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PhysicalRepository extends UserBaseRepository<Physical> {

}
