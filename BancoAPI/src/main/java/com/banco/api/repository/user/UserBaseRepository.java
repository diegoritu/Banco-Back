package com.banco.api.repository.user;

import com.banco.api.model.user.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserBaseRepository<T extends User> extends CrudRepository<T, Integer> {

    public T findByUsername(String username);

	/*public boolean existsByUsername(String username);

    @Query("select userTypeNumber from users where users.username = ?")
	public int getUserTypeNumber(String username);*/
}
