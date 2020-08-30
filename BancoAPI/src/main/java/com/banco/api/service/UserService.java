package com.banco.api.service;

import com.banco.api.adapter.ModelAdapter;
import com.banco.api.model.user.Physical;
import com.banco.api.repository.user.PhysicalRepository;
import com.banco.api.dto.user.PhysicalUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    PhysicalRepository physicalRepository;

    public int createPhysical(PhysicalUserDTO physicalUserView) {
        Physical physicalUser = ModelAdapter.fromExternal(Physical.class, physicalUserView);
        //TODO: crear caja de ahorro
        Physical result = physicalRepository.save(physicalUser);
        return result.getId();
    }
}