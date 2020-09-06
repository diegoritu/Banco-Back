package com.banco.api.service.user;

import com.banco.api.adapter.DateUtils;
import com.banco.api.dto.user.request.PhysicalUserRequest;
import com.banco.api.dto.user.PhysicalUserDTO;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.user.Physical;
import com.banco.api.repository.user.PhysicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhysicalUserService extends UserService<Physical, PhysicalUserDTO, PhysicalUserRequest> {

    @Autowired
    PhysicalRepository physicalRepository;

    @Override
    public PhysicalUserDTO createUser(PhysicalUserRequest request, Savings savingsAccount, Checking checkingAccount) {
        Physical user = new Physical();
        this.mapCommonUser(user, request);
        user.setActive(true);
        user.setBirthDate(DateUtils.parse(request.getBirthDate()));
        user.setDni(request.getDni());
        user.setMobilePhone(request.getMobilePhone());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        
        user.setSavings(savingsAccount);
        
        if(checkingAccount != null){
        	user.setChecking(checkingAccount);
        }

        Physical result = physicalRepository.save(user);
        return result.toView();
    }
    
    public boolean existsUser(String username) {

        Physical result = physicalRepository.findByUsernameAndUserTypeNumber(username, 0);
        if(result != null)
        {
        	return true;
        }
        else 
        {
            return false;

        }
    }
    
    public Physical findByUsername(String username) {

       Physical physicalUser = physicalRepository.findByUsername(username);
       return physicalUser;
    }
    
    public PhysicalUserDTO update(Physical physical) {

        Physical physicalUser = physicalRepository.save(physical);
        return physicalUser.toView();
     }


}