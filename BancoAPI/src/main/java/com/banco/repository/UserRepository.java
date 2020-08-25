package com.banco.repository;

import org.springframework.data.repository.CrudRepository;

import com.banco.api.entities.UserEntity;
import com.banco.api.model.User;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	UserEntity toEntity(User movement);
	
	User toModel(UserEntity entity);

}
