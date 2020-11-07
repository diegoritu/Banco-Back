package com.banco.api.repository.user;

import com.banco.api.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface UserBaseRepository<T extends User> extends CrudRepository<T, Integer> {

    T findByUsername(String username);

    List<T> findByActiveTrueAndUsernameContainingIgnoreCase(String username);

    List<T> findByActiveTrueAndCuitCuilCdi(String cuitCuilCdi);

    boolean existsByCuitCuilCdi(String cuitCuildCdi);

    boolean existsByActiveTrueAndCuitCuilCdi(String cuitCuildCdi);
}
