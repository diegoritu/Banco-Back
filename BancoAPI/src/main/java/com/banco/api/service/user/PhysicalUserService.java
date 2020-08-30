package com.banco.api.service.user;

import com.banco.api.adapter.DateUtils;
import com.banco.api.dto.request.PhysicalUserRequest;
import com.banco.api.dto.user.response.PhysicalUserDTO;
import com.banco.api.model.internal.user.Physical;
import com.banco.api.repository.user.PhysicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhysicalUserService extends UserService<Physical, PhysicalUserDTO, PhysicalUserRequest> {

    @Autowired
    PhysicalRepository physicalRepository;

    @Override
    public PhysicalUserDTO createUser(PhysicalUserRequest request) {
        Physical user = new Physical();
        this.mapCommonUser(user, request);
        user.setActive(true);
        user.setBirthDate(DateUtils.parse(request.getBirthDate()));
        user.setDni(request.getDni());
        user.setMobilePhone(request.getMobilePhone());

        //TODO: crear caja de ahorro, y cuenta corriente si aplica
        //user.setSavings(savingsAccount);
        //user.setChecking(checkingAccount);

        Physical result = physicalRepository.save(user);
        return result.toView();
    }
}