package com.banco.api.service.user;

import com.banco.api.adapter.DateUtils;
import com.banco.api.dto.user.PhysicalUserDTO;
import com.banco.api.dto.user.request.ChangePasswordRequest;
import com.banco.api.dto.user.request.PhysicalUserRequest;
import com.banco.api.dto.user.request.modification.PhysicalUserModificationRequest;
import com.banco.api.exception.DuplicatedUsernameException;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.user.Physical;
import com.banco.api.repository.user.PhysicalRepository;
import com.banco.api.service.account.CheckingService;
import com.banco.api.service.account.SavingsService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhysicalUserService extends UserService<Physical, PhysicalUserDTO, PhysicalUserRequest> {

    @Autowired
    private PhysicalRepository physicalRepository;
    @Autowired
    private LegalUserService legalUserService;
    @Autowired
    private AdministrativeUserService administrativeUserService;
    @Autowired
    private SavingsService savingsService;
    @Autowired
    private CheckingService checkingService;

    @Override
    public PhysicalUserDTO createUser(PhysicalUserRequest request) {
        if (existsUser(request.getUsername()) || legalUserService.existsUser(request.getUsername()) || administrativeUserService.existsUser(request.getUsername())) {
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
        PhysicalUserDTO result = user.toView();
        user.hashPassword(user.getPassword());
        Physical saved = physicalRepository.save(user);
        result.setId(saved.getId());
        return result;
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
            user = physicalRepository.findByCuitCuilCdi(term);
        } else if (PhysicalSearchField.LAST_NAME.equalsIgnoreCase(field)) {
            user = physicalRepository.findByLastName(term);
        }
        return user != null ? user.toView() : null;
    }

	public byte login(String username, String password) { // 1= Logued ; 2= Error ; 3= FirstLogin (Logued, but different code)
		Physical user = findByUsername(username);
		byte result = 2;
		
		String hashedPass = null;
    	MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
	        byte[] digest = md.digest();
	        hashedPass = DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(user.getPassword().equals(hashedPass)) {
			if(user.isFirstLogin()) {
				user.setFirstLogin(false);
				update(user);
				result = 3;
			}
			else {
				result = 1;
			}
		}
		
		return result;
	}

	public PhysicalUserDTO modify(PhysicalUserModificationRequest request) {
		if(!request.getUsername().equals(request.getOldUsername())) {
			if (existsUser(request.getUsername()) || legalUserService.existsUser(request.getUsername()) || administrativeUserService.existsUser(request.getUsername())) {
	            throw new DuplicatedUsernameException("Username already exists");
	        }
		}
		Physical user = findByUsername(request.getOldUsername());
		user.setUsername(request.getUsername());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setBirthDate(DateUtils.parse(request.getBirthDate()));
        user.setMobilePhone(request.getMobilePhone());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        Physical saved = physicalRepository.save(user);
        PhysicalUserDTO result = saved.toView();
        return result;
	}
	
	public void changePassword(ChangePasswordRequest request) {
		Physical user = findByUsername(request.getUsername());
		user.hashPassword(request.getPassword());	
		physicalRepository.save(user);
	}

	public PhysicalUserDTO resetPassword(String username) {
		Physical user = findByUsername(username);
		user.resetPassword();
        PhysicalUserDTO result = user.toView();
        user.hashPassword(user.getPassword());
        Physical saved = physicalRepository.save(user);
		
		return result;
	}
}