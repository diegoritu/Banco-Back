package com.banco.api.repository.user;

import com.banco.api.model.user.Legal;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LegalRepository extends UserBaseRepository<Legal> {

}
