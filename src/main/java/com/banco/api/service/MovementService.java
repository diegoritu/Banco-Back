package com.banco.api.service;

import com.banco.api.dto.account.AccountType;
import com.banco.api.dto.movement.MovementDTO;
import com.banco.api.dto.movement.MovementType;
import com.banco.api.dto.movement.request.CreditEntityDebitClientsRequest;
import com.banco.api.dto.movement.request.CreditEntityDepositCommerceRequest;
import com.banco.api.dto.movement.request.DebitCardPaymentRequest;
import com.banco.api.dto.others.CreditEntityDebitClientsFailures;
import com.banco.api.dto.others.CreditEntityDebitClientsResponseWithFailuresDTO;
import com.banco.api.dto.others.request.CreditEntityDebitClientTransaction;
import com.banco.api.dto.others.request.CreditEntityDepositCommerceTransaction;
import com.banco.api.exception.*;
import com.banco.api.model.DebitCard;
import com.banco.api.model.Movement;
import com.banco.api.model.ServicePayment;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.scheduledTransaction.salary.SalaryPayment;
import com.banco.api.model.user.Legal;
import com.banco.api.model.user.Physical;
import com.banco.api.repository.MovementRepository;
import com.banco.api.service.account.CheckingService;
import com.banco.api.service.account.SavingsService;
import com.banco.api.service.user.LegalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Optional.ofNullable;

@Service
public class MovementService {

    private static final String INSUFFICIENT_BALANCE_MESSAGE = "La cuenta origen no posee monto suficiente";

	@Autowired
	MovementRepository movementRepository;
	@Autowired
	CheckingService checkingService;
	@Autowired
	SavingsService savingsService;
	@Autowired
    DebitCardService debitCardService;
	@Autowired
	LegalUserService legalUserService;

	public MovementDTO deposit(float amount, float balanceBeforeMovement, Savings savings, MovementType movementType) {
		return deposit(amount, balanceBeforeMovement, AccountType.SAVINGS, savings, null, movementType);
	}

	public MovementDTO deposit(float amount, float balanceBeforeMovement, Checking checking, MovementType movementType) {
		return deposit(amount, balanceBeforeMovement, AccountType.CHECKING, null, checking, movementType);
	}

	public MovementDTO extract(float amount, float balanceBeforeMovement, Savings savings, MovementType movementType) {
		return extract(amount, balanceBeforeMovement, AccountType.SAVINGS, savings, null, movementType);
	}

	public MovementDTO extract(float amount, float balanceBeforeMovement, Checking checking, MovementType movementType) {
		return extract(amount, balanceBeforeMovement, AccountType.CHECKING, null, checking, movementType);
	}

	private MovementDTO deposit(float amount, float balanceBeforeMovement, AccountType accountType, Savings savings, Checking checking, MovementType movementType) {
		Movement result = new Movement();
		result.setMovementType(movementType.getValue());
		result.setDayAndHour(new Date());
		result.setAmount(amount);

		if (AccountType.SAVINGS.equals(accountType)) {
			result.setSaEntryAccount(savings);
		} else {
			result.setChEntryAccount(checking);
		}
		result.setEntryBalanceBeforeMovement(balanceBeforeMovement);
		movementRepository.save(result);
		return result.toView();
	}

	private MovementDTO extract(float amount, float balanceBeforeMovement, AccountType accountType, Savings savings, Checking checking, MovementType movementType) {
		Movement result = new Movement();
		result.setMovementType(movementType.getValue());
		result.setDayAndHour(new Date());
		result.setAmount(amount);

		if (AccountType.SAVINGS.equals(accountType)) {
			result.setSaExitAccount(savings);
		} else {
			result.setChExitAccount(checking);
		}
		result.setExitBalanceBeforeMovement(balanceBeforeMovement);
		movementRepository.save(result);
		return result.toView();
	}

	public MovementDTO transferBetweenOwnAccounts(float amount, float balanceBeforeMovementFrom, float balanceBeforeMovementTo, Savings savingsFrom, Savings savingsTo, Checking checkingFrom, Checking checkingTo, boolean accountTypes) {
		MovementDTO movementDTO = new MovementDTO();
		Movement result = new Movement();
		Date now = new Date();

		movementDTO.setDayAndHour(now.toString());
		movementDTO.setAmount(amount);
		movementDTO.setEntryBalanceBeforeMovement(balanceBeforeMovementTo);
		movementDTO.setExitBalanceBeforeMovement(balanceBeforeMovementFrom);
		movementDTO.setMovementType(5);

		result.setMovementType(5);
		result.setDayAndHour(now);
		result.setAmount(amount);
		result.setEntryBalanceBeforeMovement(balanceBeforeMovementTo);
		result.setExitBalanceBeforeMovement(balanceBeforeMovementFrom);

		if(accountTypes) { //From savings to checking
			movementDTO.setSaExitAccount(savingsFrom.toView());
			movementDTO.setChEntryAccount(checkingTo.toView());
			result.setChEntryAccount(checkingTo);
			result.setSaExitAccount(savingsFrom);
		}
		else { //From checking to savings
			movementDTO.setChExitAccount(checkingFrom.toView());
			movementDTO.setSaEntryAccount(savingsTo.toView());
			result.setChExitAccount(checkingFrom);
			result.setSaEntryAccount(savingsTo);
		}

		movementRepository.save(result);
		return movementDTO;
	}

	public MovementDTO transferToOtherAccounts(float amount, float balanceBeforeMovementFrom, float balanceBeforeMovementTo, Savings savingsFrom, Savings savingsTo, Checking checkingFrom, Checking checkingTo, int whereFrom, String reference) {
		MovementDTO movementDTO = new MovementDTO();
		Movement result = new Movement();
		Date now = new Date();

		movementDTO.setDayAndHour(now.toString());
		movementDTO.setAmount(amount);
		movementDTO.setEntryBalanceBeforeMovement(balanceBeforeMovementTo);
		movementDTO.setExitBalanceBeforeMovement(balanceBeforeMovementFrom);
		movementDTO.setMovementType(6);
		movementDTO.setReference(reference);

		result.setReference(reference);
		result.setMovementType(6);
		result.setDayAndHour(now);
		result.setAmount(amount);
		result.setEntryBalanceBeforeMovement(balanceBeforeMovementTo);
		result.setExitBalanceBeforeMovement(balanceBeforeMovementFrom);

		switch (whereFrom) {
		case 1: //From savings to savings
			movementDTO.setSaExitAccount(savingsFrom.toView());
			movementDTO.setSaEntryAccount(savingsTo.toView());
			result.setSaEntryAccount(savingsTo);
			result.setSaExitAccount(savingsFrom);
			break;

		case 2: //From savings to checking
			movementDTO.setSaExitAccount(savingsFrom.toView());
			movementDTO.setChEntryAccount(checkingTo.toView());
			result.setSaExitAccount(savingsFrom);
			result.setChEntryAccount(checkingTo);
			break;

		case 3: //From checking to savings
			movementDTO.setChExitAccount(checkingFrom.toView());
			movementDTO.setSaEntryAccount(savingsTo.toView());
			result.setChExitAccount(checkingFrom);
			result.setSaEntryAccount(savingsTo);
			break;

		case 4: //From checking to checking
			movementDTO.setChExitAccount(checkingFrom.toView());
			movementDTO.setChEntryAccount(checkingTo.toView());
			result.setChExitAccount(checkingFrom);
			result.setChEntryAccount(checkingTo);
			break;

		default:
			break;
		}

		movementRepository.save(result);
		return movementDTO;
	}

	public MovementDTO payServices(byte whereFrom, Checking checkingFrom, Savings savingsFrom, ServicePayment servicePayment, byte whereTo, Checking checkingTo, Savings savingsTo, float balanceBeforeMovementFrom, float balanceBeforeMovementTo, String businessName) {
		MovementDTO movementDTO = new MovementDTO();
		Movement result = new Movement();
		Date now = new Date();

		servicePayment.setPaid(true);

		movementDTO.setBusinessName(businessName);
		movementDTO.setDayAndHour(now.toString());
		movementDTO.setEntryBalanceBeforeMovement(balanceBeforeMovementTo);
		movementDTO.setExitBalanceBeforeMovement(balanceBeforeMovementFrom);
		movementDTO.setAmount(servicePayment.getAmount());
		movementDTO.setMovementType(MovementType.SERVICES_PAYMENT.getValue());
		movementDTO.setService(servicePayment.toView());
		movementDTO.setBusinessName(businessName);
		
		result.setBusinessName(businessName);
		result.setDayAndHour(now);
		result.setEntryBalanceBeforeMovement(balanceBeforeMovementTo);
		result.setExitBalanceBeforeMovement(balanceBeforeMovementFrom);
		result.setAmount(servicePayment.getAmount());
		result.setMovementType(MovementType.SERVICES_PAYMENT.getValue());
		result.setService(servicePayment);

		if(whereFrom == 0) {
			result.setChExitAccount(checkingFrom);
			movementDTO.setChExitAccount(checkingFrom.toView());
		}
		else if(whereFrom == 1) {
			result.setSaExitAccount(savingsFrom);
			movementDTO.setSaExitAccount(savingsFrom.toView());
		}
		if(whereTo == 0) {
			result.setChEntryAccount(servicePayment.getVendorChecking());
			movementDTO.setChEntryAccount(servicePayment.getVendorChecking().toView());
		}
		else {
			result.setSaEntryAccount(servicePayment.getVendorSavings());
			movementDTO.setSaEntryAccount(servicePayment.getVendorSavings().toView());
		}

		movementRepository.save(result);

		return movementDTO;
	}

	public Collection<MovementDTO> getMovements(String accountNumber, byte accountType) {
		Collection<MovementDTO> result = new ArrayList<MovementDTO>();
		Collection<Movement> movements = new ArrayList<Movement>();
		Checking checking;
		Savings savings;
		if(accountType == 0) {
			checking = checkingService.findByAccountNumber(accountNumber);
			movements = movementRepository.findByChEntryAccountIdAccountOrChExitAccountIdAccountOrderByDayAndHourDescIdMovementDesc(checking.getIdAccount(), checking.getIdAccount());
		}
		else {
			savings = savingsService.findByAccountNumber(accountNumber);
			movements = movementRepository.findBySaEntryAccountIdAccountOrSaExitAccountIdAccountOrderByDayAndHourDescIdMovementDesc(savings.getIdAccount(),savings.getIdAccount());
		}
		for(Movement m : movements) {
			result.add(m.toView());
		}

		return result;
	}

	public MovementDTO getMovementById(int id) {
		return movementRepository.findByIdMovement(id).toView();
	}

	public int debitCardPayment(DebitCardPaymentRequest request) {
		Movement movement = new Movement();
		int transactionId;
		float clientBalanceBeforeMovement;
		float businessBalanceBeforeMovement;
		Checking businessChecking = null;
		Savings businessSavings = null;
		Legal legal = null;
		Date now = new Date();

		movement.setAmount(request.getAmount());
		movement.setConcept(request.getConcept());
		movement.setDayAndHour(now);
		movement.setMovementType(8);

		if(debitCardService.existsDebitCard(request.getDebitCard().getNumber(), request.getDebitCard().getSecurityCode())) {
    		DebitCard debitCard = debitCardService.findByNumberAndSecurityCode(request.getDebitCard().getNumber(), request.getDebitCard().getSecurityCode());
    		clientBalanceBeforeMovement = debitCard.getSavingsAccount().getBalance();
    		movement.setSaExitAccount(debitCard.getSavingsAccount());
    		if(clientBalanceBeforeMovement < request.getAmount()) {
    			throw new ClientInsuficientFundsException("El cliente no tiene fondos para realizar la operación");
    		}
    		debitCard.getSavingsAccount().extract(request.getAmount());
    		movement.setExitBalanceBeforeMovement(clientBalanceBeforeMovement);
		}
    	else {
    		throw new DebitCardNotFoundException("La tarjeta de débito " + request.getDebitCard().getNumber() + " no existe");
    	}
    	if(checkingService.existsCbu(request.getBusinessCbu())) {
    		businessChecking = checkingService.findByCbu(request.getBusinessCbu());
    		movement.setChEntryAccount(businessChecking);
    		businessBalanceBeforeMovement = businessChecking.getBalance();
    		legal = legalUserService.findByCheckingAccount(businessChecking);
    		businessChecking.deposit(request.getAmount());
    	}
    	else if(savingsService.existsCbu(request.getBusinessCbu())) {
    		businessSavings = savingsService.findByCbu(request.getBusinessCbu());
    		movement.setSaEntryAccount(businessSavings);
    		businessBalanceBeforeMovement = businessSavings.getBalance();
    		legal = legalUserService.findBySavingsAccount(businessSavings);
    		businessSavings.deposit(request.getAmount());

    	}
    	else {
    		throw new BusinessCBUNotFoundException("El CBU del comercio " + request.getBusinessCbu() + " no existe");
    	}
    	movement.setBusinessName(legal.getBusinessName());
		movement.setEntryBalanceBeforeMovement(businessBalanceBeforeMovement);


		Movement m = movementRepository.save(movement);


		transactionId = m.getIdMovement();
		return transactionId;
	}


	public CreditEntityDebitClientsResponseWithFailuresDTO creditEntityDebitClients(CreditEntityDebitClientsRequest request) {
		Checking creditEntityChecking = null;
		Savings creditEntitySavings = null;
		Legal creditEntity = null;
		List <CreditEntityDebitClientsFailures> failures = new ArrayList<>();
		List <Integer> transactionIds = new ArrayList<>();
		float creditEntityBalance = 0;
		Checking clientChecking = null;
		Savings clientSavings = null;
		Movement movement = null;
		Date now = new Date();


		if(checkingService.existsCbu(request.getCreditEntityCBU())) {
			creditEntityChecking = checkingService.findByCbu(request.getCreditEntityCBU());
    		creditEntity = legalUserService.findByCheckingAccount(creditEntityChecking);
    		creditEntityBalance = creditEntityChecking.getBalance();
		}
    	else if(savingsService.existsCbu(request.getCreditEntityCBU())) {
    		creditEntitySavings = savingsService.findByCbu(request.getCreditEntityCBU());
    		creditEntity = legalUserService.findBySavingsAccount(creditEntitySavings);
    		creditEntityBalance = creditEntitySavings.getBalance();
    	}

    	if(creditEntityChecking != null || creditEntitySavings != null)
    	{
    		List<CreditEntityDebitClientTransaction> transactions = request.getTransactions();
    		for(CreditEntityDebitClientTransaction t: transactions) {
    			if(checkingService.existsCbu(t.getDebtorCBU())){
    				clientChecking = checkingService.findByCbu(t.getDebtorCBU());

    				if(clientChecking.getBalance() < t.getDebtAmount()) {
        				CreditEntityDebitClientsFailures failure = new CreditEntityDebitClientsFailures(t.getDebtorCBU(), 409, "DEBTOR_ACCOUNT_INSUFFICIENT_FUNDS", "La cuenta CBU " + t.getDebtorCBU() + " posee fondos insuficientes.");
        				failures.add(failure);
    				}
    				else {
    					movement = new Movement();
    					movement.setAmount(t.getDebtAmount());
    					movement.setDayAndHour(now);
    					movement.setMovementType(9);
    					movement.setChExitAccount(clientChecking);
    			    	movement.setBusinessName(creditEntity.getBusinessName());
    					movement.setExitBalanceBeforeMovement(clientChecking.getBalance());

    					clientChecking.extract(t.getDebtAmount());
    					if(creditEntityChecking != null) {
    						creditEntityChecking.deposit(t.getDebtAmount());
        					movement.setChEntryAccount(creditEntityChecking);
        					movement.setEntryBalanceBeforeMovement(creditEntityBalance);
        					creditEntityBalance += t.getDebtAmount();
    					}
    					else {
    						creditEntitySavings.deposit(t.getDebtAmount());
        					movement.setSaEntryAccount(creditEntitySavings);
        					movement.setEntryBalanceBeforeMovement(creditEntityBalance);
        					creditEntityBalance += t.getDebtAmount();

    					}

    					movementRepository.save(movement);
        			}

    			}
    			else if(savingsService.existsCbu(t.getDebtorCBU())){
    				clientSavings = savingsService.findByCbu(t.getDebtorCBU());

    				if(clientSavings.getBalance() < t.getDebtAmount()) {
        				CreditEntityDebitClientsFailures failure = new CreditEntityDebitClientsFailures(t.getDebtorCBU(), 409, "DEBTOR_ACCOUNT_INSUFFICIENT_FUNDS", "La cuenta CBU " + t.getDebtorCBU() + " posee fondos insuficientes.");
        				failures.add(failure);
    				}
    				else {
    					movement = new Movement();
    					movement.setAmount(t.getDebtAmount());
    					movement.setDayAndHour(now);
    					movement.setMovementType(9);
    					movement.setSaExitAccount(clientSavings);
    			    	movement.setBusinessName(creditEntity.getBusinessName());
    					movement.setExitBalanceBeforeMovement(clientSavings.getBalance());

    					clientSavings.extract(t.getDebtAmount());
    					if(creditEntityChecking != null) {
    						creditEntityChecking.deposit(t.getDebtAmount());
        					movement.setChEntryAccount(creditEntityChecking);
        					movement.setEntryBalanceBeforeMovement(creditEntityBalance);
        					creditEntityBalance += t.getDebtAmount();

    					}
    					else {
    						creditEntitySavings.deposit(t.getDebtAmount());
        					movement.setSaEntryAccount(creditEntitySavings);
        					movement.setEntryBalanceBeforeMovement(creditEntityBalance);
        					creditEntityBalance += t.getDebtAmount();

    					}

    					Movement m = movementRepository.save(movement);
    					transactionIds.add(m.getIdMovement());
    				}
    			}
    			else {
    				CreditEntityDebitClientsFailures failure = new CreditEntityDebitClientsFailures(t.getDebtorCBU(), 404, "DEBTOR_CBU_NOT_FOUND", "La cuenta de deudor " + t.getDebtorCBU() + " no existe.");
    				failures.add(failure);
    			}
    		}

    	}
    	else {
    		throw new BusinessCBUNotFoundException("El CBU de la entidad crediticia " + request.getCreditEntityCBU() + " no existe");
    	}

		CreditEntityDebitClientsResponseWithFailuresDTO response = new CreditEntityDebitClientsResponseWithFailuresDTO(transactionIds, failures);
		return response;
	}

	public Movement payScheduledSalary(String employerCbu, String employeeCbu, float amount) {
		Movement movement = transferBetweenTwoAccountsByCBU(employerCbu, employeeCbu,
				amount, MovementType.SALARY_PAYMENT);

		Legal employer = legalUserService.findByCBU(employerCbu);
		movement.setBusinessName(employer.getBusinessName());
		movementRepository.save(movement);
		return movement;
	}

	public Movement collectScheduledService(String clientCbu, String serviceProviderCbu, ServicePayment servicePayment) {
		Movement movement = transferBetweenTwoAccountsByCBU(clientCbu, serviceProviderCbu, servicePayment.getAmount(),
				MovementType.SERVICES_PAYMENT);

		movement.setBusinessName(servicePayment.getVendor().getBusinessName());
		movement.setService(servicePayment);
		movementRepository.save(movement);
		return movement;
	}

	//Transfiere el monto de la cuenta origen a la destino, y crea un registro de movimiendo de la transaccion
	public Movement transferBetweenTwoAccountsByCBU(String originCBU, String destinationCBU, float amount, MovementType movementType) {
		Movement movement = new Movement();
		movement.setAmount(amount);
		movement.setDayAndHour(new Date());
		movement.setMovementType(movementType.getValue());

		Savings originSavings = savingsService.findByCbu(originCBU);
		if (originSavings != null) {

			movement.setExitBalanceBeforeMovement(originSavings.getBalance());
			boolean couldBeExtracted = originSavings.extract(amount);
			if (!couldBeExtracted) {
				throw new InsufficientBalanceException(INSUFFICIENT_BALANCE_MESSAGE);
			} else {
				movement.setSaExitAccount(originSavings);
				savingsService.update(originSavings);
			}
		} else {
			Checking originChecking = checkingService.findByCbu(originCBU);
			if (originChecking == null)
				throw new AccountCBUNotFoundException(String.format("Cuenta CBU %s no encontrada", originCBU));

			movement.setExitBalanceBeforeMovement(originChecking.getBalance());
			boolean couldBeExtracted = originChecking.extract(amount);
			if (!couldBeExtracted) {
				throw new InsufficientBalanceException(INSUFFICIENT_BALANCE_MESSAGE);
			} else {
				movement.setChExitAccount(originChecking);
				checkingService.update(originChecking);
			}
		}

		Savings destinationSavings = savingsService.findByCbu(destinationCBU);
		if (destinationSavings != null) {
			movement.setEntryBalanceBeforeMovement(destinationSavings.getBalance());
			destinationSavings.deposit(amount);
			movement.setSaEntryAccount(destinationSavings);
			savingsService.update(destinationSavings);
		} else {
			Checking destinationChecking = checkingService.findByCbu(destinationCBU);
			if (destinationChecking == null)
				throw new AccountCBUNotFoundException(String.format("Cuenta CBU %s no encontrada", destinationCBU));

			movement.setEntryBalanceBeforeMovement(destinationChecking.getBalance());
			destinationChecking.deposit(amount);
			movement.setChEntryAccount(destinationChecking);
			checkingService.update(destinationChecking);
		}

		movementRepository.save(movement);
		return movement;
	}
	
	public void creditEntityDepositCommerce(CreditEntityDepositCommerceRequest request) {
		Checking creditEntityChecking = null;
		Savings creditEntitySavings = null;
		Checking businessChecking = null;
		Savings businessSavings = null;
		Legal creditEntity = null;
		List<Movement> movementsBeforeSaving = new ArrayList<Movement>();
		float entryBalanceBeforeMovement = 0;
		float exitBalanceBeforeMovement = 0;
		final float CREDIT_ENTITY_BALANCE;
		float totalAmount = 0;
		Date now = new Date();

		
		if(checkingService.existsCbu(request.getCreditEntityCBU())) {
			creditEntityChecking = checkingService.findByCbu(request.getCreditEntityCBU());
    		creditEntity = legalUserService.findByCheckingAccount(creditEntityChecking);
    		CREDIT_ENTITY_BALANCE = creditEntityChecking.getBalance();
		}
    	else if(savingsService.existsCbu(request.getCreditEntityCBU())) {
    		creditEntitySavings = savingsService.findByCbu(request.getCreditEntityCBU());
    		creditEntity = legalUserService.findBySavingsAccount(creditEntitySavings);
    		CREDIT_ENTITY_BALANCE = creditEntitySavings.getBalance();
    	}
    	else {
    		throw new BusinessCBUNotFoundException("El CBU de la entidad crediticia " + request.getCreditEntityCBU() + " no existe");
    	}
		
		for(CreditEntityDepositCommerceTransaction transaction : request.getTransactions()) {
			
			totalAmount += transaction.getAmount();
			
			Movement movement = new Movement();
			
			movement.setAmount(transaction.getAmount());
			movement.setDayAndHour(now);
			movement.setMovementType(11);
			movement.setBusinessName(creditEntity.getBusinessName());
			
			if(creditEntityChecking != null) {
				System.out.println("AMOUNT: " + totalAmount + "; balance: " + CREDIT_ENTITY_BALANCE + "; maxOverdraft: " + creditEntityChecking.getMaxOverdraft());
				System.out.println("ES: " + Float.toString(CREDIT_ENTITY_BALANCE - totalAmount));
				movement.setChExitAccount(creditEntityChecking);
				exitBalanceBeforeMovement = creditEntityChecking.getBalance();
				creditEntityChecking.extract(transaction.getAmount());
				float dif = CREDIT_ENTITY_BALANCE - totalAmount;
				
				if(dif < 0 && Math.abs(dif) > creditEntityChecking.getMaxOverdraft()) {
					throw new CreditEntityAccountInsuficientFundsException("La cuenta CBU " + request.getCreditEntityCBU() + " de la entidad crediticia posee fondos insuficientes");
				}
				if(checkingService.existsCbu(transaction.getBusinessCBU())) {
					businessChecking = checkingService.findByCbu(transaction.getBusinessCBU());
					entryBalanceBeforeMovement = businessChecking.getBalance();
					businessChecking.deposit(transaction.getAmount());
					movement.setChEntryAccount(businessChecking);
				}
				else if(savingsService.existsCbu(transaction.getBusinessCBU())) {
					businessSavings = savingsService.findByCbu(transaction.getBusinessCBU());
					entryBalanceBeforeMovement = businessSavings.getBalance();
					businessSavings.deposit(transaction.getAmount());
					movement.setSaEntryAccount(businessSavings);
				}
				else {
					throw new CommerceCBUNotFoundException("El CBU del negocio " + transaction.getBusinessCBU() + " no existe");
				}
			}
			
			else if(creditEntitySavings != null) {
				System.out.println("AMOUNT: " + transaction.getAmount() + "; balance: " + creditEntitySavings.getBalance());

				movement.setSaExitAccount(creditEntitySavings);
				exitBalanceBeforeMovement = creditEntitySavings.getBalance();
				creditEntitySavings.extract(transaction.getAmount());
				
				if(CREDIT_ENTITY_BALANCE - totalAmount < 0) {
					throw new CreditEntityAccountInsuficientFundsException("La cuenta CBU " + request.getCreditEntityCBU() + " de la entidad crediticia posee fondos insuficientes");
				}
				if(checkingService.existsCbu(transaction.getBusinessCBU())) {
					businessChecking = checkingService.findByCbu(transaction.getBusinessCBU());
					entryBalanceBeforeMovement = businessChecking.getBalance();
					businessChecking.deposit(transaction.getAmount());
					movement.setChEntryAccount(businessChecking);
				}
				else if(savingsService.existsCbu(transaction.getBusinessCBU())) {
					businessSavings = savingsService.findByCbu(transaction.getBusinessCBU());
					entryBalanceBeforeMovement = businessSavings.getBalance();
					businessSavings.deposit(transaction.getAmount());
					movement.setSaEntryAccount(businessSavings);
				}
				else {
					throw new BusinessCBUNotFoundException("El CBU del negocio " + transaction.getBusinessCBU() + " no existe");
				}
			}
			
			movement.setEntryBalanceBeforeMovement(entryBalanceBeforeMovement);
			movement.setExitBalanceBeforeMovement(exitBalanceBeforeMovement);
			movementsBeforeSaving.add(movement);
		}
		for(Movement m : movementsBeforeSaving) {
			movementRepository.save(m);
		}
		
	}

}
