package com.banco.api.dao.repository;

import com.banco.api.dao.entity.ServiceEntity;
import com.banco.api.model.Service;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

public interface ServiceRepository extends CrudRepository<ServiceEntity, Integer> {

    ServiceEntity toEntity(Service movement);

    Service toModel(ServiceEntity entity);

}
