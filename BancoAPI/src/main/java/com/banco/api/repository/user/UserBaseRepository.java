package com.banco.api.repository.user;

import com.banco.api.model.internal.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserBaseRepository<T extends User> extends CrudRepository<T, Integer> {

    public T findByUsername(String username);
}
