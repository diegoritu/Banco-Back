package com.banco.api.dao.repository;

import com.banco.api.dao.entity.UserEntity;
import com.banco.api.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    UserEntity toEntity(User movement);

    User toModel(UserEntity entity);

}
