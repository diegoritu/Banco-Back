package com.banco.api.service.user;

import com.banco.api.dto.user.LegalUserDTO;
import com.banco.api.dto.user.UserType;
import com.banco.api.dto.user.request.ChangePasswordRequest;
import com.banco.api.dto.user.request.LegalUserRequest;
import com.banco.api.dto.user.request.modification.LegalUserModificationRequest;
import com.banco.api.exception.CheckingAccountRequestException;
import com.banco.api.exception.DuplicatedUserException;
import com.banco.api.exception.InvalidUserRequestException;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.user.Legal;
import com.banco.api.repository.user.LegalRepository;
import com.banco.api.service.account.CheckingService;
import com.banco.api.service.account.SavingsService;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LegalUserService extends UserService<Legal, LegalUserDTO, LegalUserRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LegalUserService.class);

    @Autowired
    private LegalRepository legalRepository;
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
        if (this.existsUser(request.getUsername()) || physicalUserService.existsUser(request.getUsername())
                || administrativeUserService.existsUser(request.getUsername())) {
            throw new DuplicatedUserException("El nombre de usuario ya existe");
        }

        if (request.isWithCheckingAccount() && request.getMaxOverdraft() == null) {
            throw new InvalidUserRequestException("Si requiere cuenta corriente es necesario especificar el monto de descubierto");
        }

        if (request.isWithCheckingAccount() && request.getMaxOverdraft() < 0) {
            throw new InvalidUserRequestException("El monto de descubierto permitido debe ser mayor o igual a cero");
        }

        Legal user = new Legal();
        this.mapCommonUser(user, request);
        user.setActive(true);
        user.setBusinessName(request.getBusinessName());

        Savings savingsAccount = savingsService.createAccount();
        user.setSavings(savingsAccount);

        if (request.isWithCheckingAccount()) {
            Checking checkingAccount = checkingService.createAccount(request.getMaxOverdraft());
            user.setChecking(checkingAccount);
        }
        LegalUserDTO result = user.toView();
        result.setPassword(user.getPassword());
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
        Legal legalUser = legalRepository.findByUsernameAndUserTypeNumber(username, UserType.LEGAL.getValue());
        return legalUser;
    }
    public Legal findByActiveUsername(String username) {
        Legal legalUser = legalRepository.findByUsernameAndUserTypeNumberAndActive(username, UserType.LEGAL.getValue(), true);
        return legalUser;
    }

    public LegalUserDTO update(Legal legal) {
        Legal legalUser = legalRepository.save(legal);
        return legalUser.toView();
    }

    public Set<LegalUserDTO> search(String field, String term) {
        Set<Legal> users = Sets.newHashSet();
        if (LegalSearchField.USERNAME.equalsIgnoreCase(field)) {
            users.addAll(legalRepository.findByUsernameContainingIgnoreCase(term));

        } else if (LegalSearchField.BUSINESS_NAME.equalsIgnoreCase(field)) {
            users.addAll(legalRepository.findByBusinessNameContainingIgnoreCase(term));

        } else if (LegalSearchField.CUIT_CUIL.equalsIgnoreCase(field)) {
            users.addAll(legalRepository.findByCuitCuilCdi(term));
        }
        return users
                .stream()
                .map(Legal::toView)
                .collect(Collectors.toSet());
    }

    public byte login(String username, String password) { // 1= Logued ; 2= Error ; 3= FirstLogin (Logued, but different code)
        Legal user = findByActiveUsername(username);
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

        if (user.getPassword().equals(hashedPass)) {
            if (user.isFirstLogin()) {
                user.setFirstLogin(false);
                update(user);
                result = 3;
            } else {
                result = 1;
            }
        }

        return result;
    }

    public LegalUserDTO modify(LegalUserModificationRequest request) {
        if (!request.getUsername().equals(request.getOldUsername())) {
            if (this.existsUser(request.getUsername()) || physicalUserService.existsUser(request.getUsername())
                    || administrativeUserService.existsUser(request.getUsername())) {
                throw new DuplicatedUserException("El nombre de usuario ya existe");
            }
        }
        Legal user = findByUsername(request.getOldUsername());
        user.setUsername(request.getUsername());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setBusinessName(request.getBusinessName());
        Legal saved = legalRepository.save(user);
        LegalUserDTO result = saved.toView();
        return result;
    }

    public Checking openCheckingAccount(String username, Float maxOverdraft) {
        Legal user = findByUsername(username);
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
		Legal user = findByActiveUsername(username); 
		user.setChecking(checking);
		legalRepository.save(user);
	}

	public void changePassword(ChangePasswordRequest request) {
        Legal user = findByUsername(request.getUsername());
        user.hashPassword(request.getPassword());
        legalRepository.save(user);
    }

    public LegalUserDTO resetPassword(String username) {
        Legal user = findByUsername(username);
        user.resetPassword();
        LegalUserDTO result = user.toView();
        result.setPassword(user.getPassword());
        user.hashPassword(user.getPassword());
        Legal saved = legalRepository.save(user);

        return result;
    }

    public boolean vendorExists(String vendorId) {
        return legalRepository.existsByVendorId(vendorId);
    }

	public Collection<Legal> findAllLegals() {
		Iterable<Legal> legals = legalRepository.findAll();
		Collection<Legal> result = new ArrayList<Legal>();
		legals.forEach(result :: add);
		return result;
	}
}
