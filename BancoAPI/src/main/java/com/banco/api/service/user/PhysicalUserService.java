package com.banco.api.service.user;

import com.banco.api.dto.user.PhysicalUserDTO;
import com.banco.api.dto.user.UserType;
import com.banco.api.dto.user.request.ChangePasswordRequest;
import com.banco.api.dto.user.request.PhysicalUserRequest;
import com.banco.api.dto.user.request.modification.PhysicalUserModificationRequest;
import com.banco.api.exception.CheckingAccountRequestException;
import com.banco.api.exception.DuplicatedUserException;
import com.banco.api.exception.InvalidUserRequestException;
import com.banco.api.model.DebitCard;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.user.Physical;
import com.banco.api.repository.user.PhysicalRepository;
import com.banco.api.service.account.CheckingService;
import com.banco.api.service.account.SavingsService;
import com.banco.api.service.others.DebitCardService;
import com.banco.api.utils.DateUtils;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PhysicalUserService extends UserService<Physical, PhysicalUserDTO, PhysicalUserRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhysicalUserService.class);

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
    @Autowired
    private DebitCardService debitCardService;

    @Override
    public PhysicalUserDTO createUser(PhysicalUserRequest request) {
        if (existsUser(request.getUsername()) || legalUserService.existsUser(request.getUsername())
                || administrativeUserService.existsUser(request.getUsername())) {
            throw new DuplicatedUserException("El nombre de usuario ya existe");
        }

        if (existsByCuitCuilCdi(request.getCuitCuilCdi()) || legalUserService.existsByCuitCuilCdi(request.getCuitCuilCdi())) {
            throw new DuplicatedUserException("Ya existe un usuario con el mismo CUIT/CUIL");
        }

        if (existsByDni(request.getDni()) || administrativeUserService.existsByDni(request.getDni())) {
            throw new DuplicatedUserException("Ya existe un usuario con el mismo DNI");
        }

        if (request.isWithCheckingAccount() && request.getMaxOverdraft() == null) {
            throw new InvalidUserRequestException("Si requiere cuenta corriente es necesario especificar el monto de descubierto");
        }

        if (!DateUtils.isValid(request.getBirthDate())) {
            throw new InvalidUserRequestException("Formato de fecha de nacimiento inválido");
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
        
        DebitCard debitCard = debitCardService.createDebitCard(savingsAccount.getAccountNumber());
        user.setDebitCard(debitCard);
        
        if (request.isWithCheckingAccount()) {
            Checking checkingAccount = checkingService.createAccount(request.getMaxOverdraft());
            user.setChecking(checkingAccount);
        }
        PhysicalUserDTO result = user.toView();
        result.setPassword(user.getPassword());
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

    public Physical findByActiveUsername(String username) {
    	Physical physicalUser = physicalRepository.findByUsernameAndUserTypeNumberAndActive(username, UserType.PHYSICAL.getValue(), true);
        return physicalUser;
    }

    public PhysicalUserDTO update(Physical physical) {
        Physical physicalUser = physicalRepository.save(physical);
        return physicalUser.toView();
    }

    public Set<PhysicalUserDTO> search(String field, String term) {
        Set<Physical> users = Sets.newHashSet();
        if (PhysicalSearchField.USERNAME.equalsIgnoreCase(field)) {
            users.addAll(physicalRepository.findByActiveTrueAndUsernameContainingIgnoreCase(term));

        } else if (PhysicalSearchField.DNI.equalsIgnoreCase(field)) {
            users.addAll(physicalRepository.findByActiveTrueAndDni(term));

        } else if (PhysicalSearchField.CUIT_CUIL.equalsIgnoreCase(field)) {
            users.addAll(physicalRepository.findByActiveTrueAndCuitCuilCdi(term));

        } else if (PhysicalSearchField.FULL_NAME.equalsIgnoreCase(field)) {
            String[] words = term.split(" ");
            Stream.of(words).forEach(word -> {
                users.addAll(physicalRepository.findByActiveTrueAndFirstNameContainingIgnoreCase(word));
                users.addAll(physicalRepository.findByActiveTrueAndLastNameContainingIgnoreCase(word));
            });
        }
        return users
                .stream()
                .map(Physical::toView)
                .collect(Collectors.toSet());
    }

	public byte login(String username, String password) { // 1= Logued ; 2= Error ; 3= FirstLogin (Logued, but different code)
		Physical user = findByActiveUsername(username);
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
			if (existsUser(request.getUsername()) || legalUserService.existsUser(request.getUsername())
                    || administrativeUserService.existsUser(request.getUsername())) {
	            throw new DuplicatedUserException("El nombre de usuario ya existe");
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

    public Checking openCheckingAccount(String username, Float maxOverdraft) {
        Physical user = findByUsername(username);
        if (user.getChecking() != null && user.getChecking().isActive())
            throw new CheckingAccountRequestException("El usuario ya tiene cuenta corriente activa");

        if (maxOverdraft == null) {
            throw new CheckingAccountRequestException("Es necesario especificar el descubierto");
        }

        LOGGER.info("Opening checking account for username: {} with maxOverdraft: {}", username, maxOverdraft);
        Checking checkingAccount;
        if (user.getChecking() != null && !user.getChecking().isActive()) {
            LOGGER.debug("Found checking account for username: {}, but inactive. Activating account with overdraft: {}", username, maxOverdraft);
            user.getChecking().setActive(true);
            user.getChecking().setMaxOverdraft(maxOverdraft);
            checkingAccount = checkingService.update(user.getChecking());
        } else {
            checkingAccount = checkingService.createAccount(maxOverdraft);
        	assignCheckingAccount(username, checkingAccount);
        }
        return checkingAccount;
    }
	
	private void assignCheckingAccount(String username, Checking checking) {
		Physical user = findByActiveUsername(username); 
		user.setChecking(checking);
		physicalRepository.save(user);
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
        result.setPassword(user.getPassword());
        user.hashPassword(user.getPassword());
        Physical saved = physicalRepository.save(user);

        
		return result;
	}

	public boolean existsByCuitCuilCdi(String cuitCuilCdi) {
        return physicalRepository.existsByCuitCuilCdi(cuitCuilCdi);
    }

    public boolean existsByDni(String dni) {
        return physicalRepository.existsByDni(dni);
    }
}