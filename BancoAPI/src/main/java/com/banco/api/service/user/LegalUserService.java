package com.banco.api.service.user;

import com.banco.api.dto.user.request.LegalUserRequest;
import com.banco.api.dto.user.LegalUserDTO;
import com.banco.api.model.user.Legal;
import com.banco.api.model.user.Physical;
import com.banco.api.repository.user.LegalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegalUserService extends UserService<Legal, LegalUserDTO, LegalUserRequest> {

    @Autowired
    LegalRepository legalRepository;

    @Override
    public LegalUserDTO createUser(LegalUserRequest request) {
        Legal user = new Legal();
        this.mapCommonUser(user, request);
        user.setActive(true);
        user.setBusinessName(request.getBusinessName());
        //TODO: crear caja de ahorro, y cuenta corriente si aplica
        //user.setSavings(savingsAccount);
        //user.setChecking(checkingAccount);

        Legal result = legalRepository.save(user);
        return result.toView();
    }
    
    public boolean existsUser(String username) {

        Legal result = legalRepository.findByUsername(username);
        if(result != null)
        {
        	return true;
        }
        else 
        {
            return false;

        }
    }

}
