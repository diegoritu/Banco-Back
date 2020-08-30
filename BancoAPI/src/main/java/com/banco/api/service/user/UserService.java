package com.banco.api.service.user;

import com.banco.api.dto.request.UserRequest;
import com.banco.api.dto.user.UserDTO;
import com.banco.api.model.internal.user.User;
import org.springframework.stereotype.Service;

public abstract class UserService<T extends User, D extends UserDTO, R extends UserRequest> {

    protected abstract D createUser(R userRequest);

    protected void mapCommonUser(T user, R userRequest) {
        user.setUsername(userRequest.getUsername());
        user.setCuitCuilCdi(userRequest.getCuitCuilCdi());
        user.setAddress(userRequest.getAddress());
        user.setPhone(userRequest.getPhone());
    }

}
