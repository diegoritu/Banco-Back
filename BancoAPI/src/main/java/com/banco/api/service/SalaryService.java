package com.banco.api.service;

import com.banco.api.dto.movement.MovementType;
import com.banco.api.exception.AccountCBUNotFoundException;
import com.banco.api.exception.BusinessCBUNotFoundException;
import com.banco.api.exception.EmployeeCBUNotFoundException;
import com.banco.api.exception.InsufficientBalanceException;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.scheduledTransaction.ScheduledTransactionStatus;
import com.banco.api.model.scheduledTransaction.salary.SalaryPayment;
import com.banco.api.repository.scheduledTransaction.SalaryPaymentRepository;
import com.banco.api.service.account.CheckingService;
import com.banco.api.service.account.SavingsService;
import com.banco.api.service.user.LegalUserService;
import com.banco.api.service.user.PhysicalUserService;
import com.banco.published.controller.request.SalaryPaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.lang.String.format;

@Service
public class SalaryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SavingsService.class);

    @Autowired
    private SalaryPaymentRepository salaryPaymentRepository;
    @Autowired
    private LegalUserService legalUserService;
    @Autowired
    private PhysicalUserService physicalUserService;
    @Autowired
    private CheckingService checkingService;
    @Autowired
    private SavingsService savingsService;
    @Autowired
    private MovementService movementService;

    public void processSalaryRequest(SalaryPaymentRequest request) {
        validateSalaryRequest(request);

        String employerCBU = request.getEmployerCBU();
        Date scheduledDate = new Date(); //TODO calcular 48hs posteriores
        request.getSalaries().forEach(salary -> {
                    SalaryPayment salaryPayment = new SalaryPayment(employerCBU, salary.getEmployeeCBU(),
                            salary.getSalary(), scheduledDate);

                    salaryPaymentRepository.save(salaryPayment);
                }
        );
    }

    //TODO: scheduled task diario llamará este método cuando encuentre un salario pendiente y en fecha de pago
    public void paySalary(SalaryPayment salaryPayment) {
        LOGGER.info("Paying salary: {}", salaryPayment.toString());
        try {
            movementService.transferBetweenTwoAccountsByCBU(salaryPayment.getEmployerCBU(), salaryPayment.getEmployeeCBU(),
                    salaryPayment.getSalary(), MovementType.EXTRACTION, MovementType.SALARY_PAYMENT);
            salaryPayment.setStatus(ScheduledTransactionStatus.DONE);
        } catch (AccountCBUNotFoundException ex) { //TODO: diferenciar CBU empleado de empleador, ¿origin/destination cbu not found?
            LOGGER.error(ex.getMessage());
            salaryPayment.setStatus(ScheduledTransactionStatus.ERROR);
            //TODO: add failure
        } catch (InsufficientBalanceException ex) {
            LOGGER.error(ex.getMessage());
            salaryPayment.setStatus(ScheduledTransactionStatus.ERROR);
            salaryPayment.setFailure("BUSINESS_ACCOUNT_INSUFFICIENT_FUNDS",
                    "La cuenta CBU del negocio no posee fondos suficientes");
        } finally {
            salaryPaymentRepository.save(salaryPayment);
            LOGGER.info("Scheduled salary payment successfully processed - id: {}", salaryPayment.getId());
        }
    }

    private void validateSalaryRequest(SalaryPaymentRequest request) {
        if (!legalUserService.existsByCBU(request.getEmployerCBU())) {
            throw new BusinessCBUNotFoundException(format("El CBU del empleador %s no existe", request.getEmployerCBU()));
        }

        if (!checkEmployerAccountBalance(request)) {
            throw new InsufficientBalanceException(format("La cuenta del empleador %s no tiene saldo suficiente", request.getEmployerCBU()));
        }

        if (request.getSalaries() != null) {
            request.getSalaries().forEach(salary -> {
                if (!physicalUserService.existsByCBU(salary.getEmployeeCBU()))
                    throw new EmployeeCBUNotFoundException(format("El CBU del empleado %s no existe", salary.getEmployeeCBU()));
            });
        }
    }

    private boolean checkEmployerAccountBalance(SalaryPaymentRequest request) {
        Float totalSalaryAmount = request.getSalaries()
                .stream()
                .reduce(0F, (partialSalary, salary) -> partialSalary + salary.getSalary(), Float::sum);

        Float balance;
        Checking checking = checkingService.findByCbu(request.getEmployerCBU());
        if (checking != null) {
            balance = checking.getBalance();
        } else {
            Savings savings = savingsService.findByCbu(request.getEmployerCBU());
            balance = savings.getBalance();
        }

        return balance >= totalSalaryAmount;
    }
}
