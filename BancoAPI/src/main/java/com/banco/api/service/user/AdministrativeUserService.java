package com.banco.api.service.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banco.api.adapter.DateUtils;
import com.banco.api.dto.user.AdministrativeUserDTO;
import com.banco.api.dto.user.request.AdministrativeUserRequest;
import com.banco.api.exception.DuplicatedUsernameException;
import com.banco.api.model.user.Administrative;
import com.banco.api.repository.user.AdministrativeRepository;

@Service
public class AdministrativeUserService extends UserService<Administrative, AdministrativeUserDTO, AdministrativeUserRequest>{
	
	@Autowired
	AdministrativeRepository administrativeRepository;
	@Autowired
	private PhysicalUserService physicalUserService;
	@Autowired
	private LegalUserService legalUserService;
	
	
	
	public AdministrativeUserDTO createUser(AdministrativeUserRequest request) {
        if (existsUser(request.getUsername()) || physicalUserService.existsUser(request.getUsername()) || legalUserService.existsUser(request.getUsername())) {
            throw new DuplicatedUsernameException("Username already exists");
        }

        Administrative user = new Administrative();
        this.mapCommonUser(user, request);
        user.setActive(true);
        user.setBirthdate(DateUtils.parse(request.getBirthDate()));
        user.setDni(request.getDni());
        user.setMobilePhone(request.getMobilePhone());
        user.setPhone(request.getPhone());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        AdministrativeUserDTO result = user.toView();
        user.hashPassword(user.getPassword());
        Administrative saved = administrativeRepository.save(user);
        result.setId(saved.getId());
        return result;
    }
	
	public boolean existsUser(String username) {
        Administrative result = findByUsername(username);
        return result != null;
    }
	
	public Administrative findByUsername(String username) {
		Administrative administrativeUser = administrativeRepository.findByUsername(username);
		return administrativeUser;
    }
    
    public AdministrativeUserDTO update(Administrative administrative) {
    	Administrative administrativeUser = administrativeRepository.save(administrative);
        return administrativeUser.toView();
    }
    
    public byte login(String username, String password) { // 1= Logued ; 2= Error ; 3= FirstLogin (Logued, but different code)
    	Administrative user = findByUsername(username);
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
