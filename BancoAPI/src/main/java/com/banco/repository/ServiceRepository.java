package com.banco.repository;

import org.springframework.data.repository.CrudRepository;

import com.banco.api.entities.ServiceEntity;
import com.banco.api.model.Service;

public interface ServiceRepository extends CrudRepository<ServiceEntity, Integer> {

	ServiceEntity toEntity(Service movement);
	
	Service toModel(ServiceEntity entity);

}
