package com.banco.api.service.user;

import com.banco.api.adapter.DateUtils;
import com.banco.api.dto.user.PhysicalUserDTO;
import com.banco.api.dto.user.request.PhysicalUserRequest;
import com.banco.api.exception.DuplicatedUsernameException;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.user.Physical;
import com.banco.api.repository.user.PhysicalRepository;
import com.banco.api.service.account.CheckingService;
import com.banco.api.service.account.SavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhysicalUserService extends UserService<Physical, PhysicalUserDTO, PhysicalUserRequest> {

    @Autowired
    PhysicalRepository physicalRepository;
    @Autowired
    private SavingsService savingsService;
    @Autowired
    private CheckingService checkingService;

    @Override
    public PhysicalUserDTO createUser(PhysicalUserRequest request) {
        if (existsUser(request.getUsername())) {
            throw new DuplicatedUsernameException("Username already exists");
        }

        Physical user = new Physical();
        this.mapCommonUser(user, request);
        user.setActive(true);
        user.setBirthDate(DateUtils.parse(request.getBirthDate()));
        user.setDni(request.getDni());
        user.setMobilePhone(request.getMobilePhone());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        Savings savingsAccount = savingsService.createAccount();
        user.setSavings(savingsAccount);

        if (request.isWithCheckingAccount()) {
            Checking checkingAccount = checkingService.createAccount();
            user.setChecking(checkingAccount);
        }

        Physical result = physicalRepository.save(user);
        return result.toView();
    }
    
    public boolean existsUser(String username) {
        Physical result = this.findByUsername(username);
        return result != null;
    }
    
    public Physical findByUsername(String username) {
       Physical physicalUser = physicalRepository.findByUsername(username);
       return physicalUser;
    }
    
    public PhysicalUserDTO update(Physical physical) {
        Physical physicalUser = physicalRepository.save(physical);
        return physicalUser.toView();
    }

    public PhysicalUserDTO search(String field, String term) {
        Physical user = null;
        if (PhysicalSearchField.USERNAME.equalsIgnoreCase(field)) {
            user = findByUsername(term);
        } else if (PhysicalSearchField.DNI.equalsIgnoreCase(field)) {
            user = physicalRepository.findByDni(term);
        } else if (PhysicalSearchField.CUIT_CUIL.equalsIgnoreCase(field)) {
            user = physicalRepository.findBycuitCuilCdi(term);
        } else if (PhysicalSearchField.LAST_NAME.equalsIgnoreCase(field)) {
            user = physicalRepository.findByLastName(term);
        }
        return user != null ? user.toView() : null;
    }
}