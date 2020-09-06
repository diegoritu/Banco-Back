package com.banco.api.service.user;

import com.banco.api.dto.user.request.UserRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

import com.banco.api.adapter.DateUtils;
import com.banco.api.dto.user.UserDTO;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.user.User;
import com.banco.api.repository.user.UserBaseRepository;

public abstract class UserService<T extends User, D extends UserDTO, R extends UserRequest> {

    protected abstract D createUser(R userRequest, Savings savingsAccount, Checking checkingAccount);

    
    protected void mapCommonUser(T user, R userRequest) {
        user.setUsername(userRequest.getUsername());
        user.setCuitCuilCdi(userRequest.getCuitCuilCdi());
        user.setAddress(userRequest.getAddress());
        user.setPhone(userRequest.getPhone());
    }
    

}
