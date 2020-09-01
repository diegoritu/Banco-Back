package com.banco.api.service.user;

import com.banco.api.dto.user.request.PhysicalUserRequest;
import com.banco.api.dto.user.request.UserRequest;
import com.banco.api.adapter.DateUtils;
import com.banco.api.dto.user.PhysicalUserDTO;
import com.banco.api.dto.user.UserDTO;
import com.banco.api.model.user.Physical;
import com.banco.api.model.user.User;

public abstract class UserService<T extends User, D extends UserDTO, R extends UserRequest> {

    protected abstract D createUser(R userRequest);

    protected void mapCommonUser(T user, R userRequest) {
        user.setUsername(userRequest.getUsername());
        user.setCuitCuilCdi(userRequest.getCuitCuilCdi());
        user.setAddress(userRequest.getAddress());
        user.setPhone(userRequest.getPhone());
    }
    

}
