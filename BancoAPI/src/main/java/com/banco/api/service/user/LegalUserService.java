package com.banco.api.service.user;

import com.banco.api.dto.user.LegalUserDTO;
import com.banco.api.dto.user.request.LegalUserRequest;
import com.banco.api.exception.DuplicatedUsernameException;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.user.Legal;
import com.banco.api.model.user.Physical;
import com.banco.api.repository.user.LegalRepository;
import com.banco.api.service.account.CheckingService;
import com.banco.api.service.account.SavingsService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegalUserService extends UserService<Legal, LegalUserDTO, LegalUserRequest> {

    @Autowired
    LegalRepository legalRepository;
    @Autowired
    private PhysicalUserService physicalUserService;
    @Autowired
    private AdministrativeUserService administrativeUserService;
    @Autowired
    private SavingsService savingsService;
    @Autowired
    private CheckingService checkingService;

    @Override
    public LegalUserDTO createUser(LegalUserRequest request) {
        if (this.existsUser(request.getUsername()) || physicalUserService.existsUser(request.getUsername()) || administrativeUserService.existsUser(request.getUsername())) {
            throw new DuplicatedUsernameException("Username already exists");
        }

        Legal user = new Legal();
        this.mapCommonUser(user, request);
        user.setActive(true);
        user.setBusinessName(request.getBusinessName());

        Savings savingsAccount = savingsService.createAccount();
        user.setSavings(savingsAccount);

        if (request.isWithCheckingAccount()) {
            Checking checkingAccount = checkingService.createAccount();
            user.setChecking(checkingAccount);
        }
        LegalUserDTO result = user.toView();
        user.hashPassword(user.getPassword());
        Legal saved = legalRepository.save(user);
        result.setId(saved.getId());
        return result;
    }

    public boolean existsUser(String username) {
        Legal result = findByUsername(username);
        return result != null;
    }

    public Legal findByUsername(String username) {
        Legal legalUser = legalRepository.findByUsernameAndUserTypeNumber(username, 1);
        return legalUser;
    }

    public LegalUserDTO update(Legal legal) {
        Legal legalUser = legalRepository.save(legal);
        return legalUser.toView();
    }

    public LegalUserDTO search(String field, String term) {
        Legal user = null;
        if (LegalSearchField.USERNAME.equalsIgnoreCase(field)) {
            user = findByUsername(term);
        } else if (LegalSearchField.BUSINESS_NAME.equalsIgnoreCase(field)) {
            user = legalRepository.findByBusinessName(term);
        } else if (LegalSearchField.CUIT_CUIL.equalsIgnoreCase(field)) {
            user = legalRepository.findBycuitCuilCdi(term);
        }
        return user != null ? user.toView() : null;
    }
    
    public byte login(String username, String password) { // 1= Logued ; 2= Error ; 3= FirstLogin (Logued, but different code)
		Legal user = findByUsername(username);
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
}
