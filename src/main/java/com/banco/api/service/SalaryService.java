package com.banco.api.service;

import com.banco.api.exception.*;
import com.banco.api.model.account.Checking;
import com.banco.api.model.account.Savings;
import com.banco.api.model.scheduledTransaction.ScheduledTransactionStatus;
import com.banco.api.model.scheduledTransaction.salary.SalaryPayment;
import com.banco.api.published.request.salaryPayment.SalaryPaymentRequest;
import com.banco.api.published.response.salaryPaymentFailure.Resource;
import com.banco.api.published.response.salaryPaymentFailure.SalaryPaymentFailure;
import com.banco.api.repository.scheduledTransaction.SalaryPaymentRepository;
import com.banco.api.service.account.CheckingService;
import com.banco.api.service.account.SavingsService;
import com.banco.api.service.user.LegalUserService;
import com.banco.api.service.user.PhysicalUserService;
import com.banco.api.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public void saveSalaryRequest(SalaryPaymentRequest request) {
        validateSalaryRequest(request);

        Date scheduledDate = getScheduledDate();
        String employerCBU = request.getEmployerCBU();
        request.getSalaries().forEach(salary -> {
                    SalaryPayment salaryPayment = new SalaryPayment(employerCBU, salary.getEmployeeCBU(),
                            salary.getSalary(), scheduledDate);

                    salaryPaymentRepository.save(salaryPayment);
                }
        );
    }

    public void paySalary(SalaryPayment salaryPayment) {
        LOGGER.info("Paying salary: {}", salaryPayment.toString());
        try {
            movementService.payScheduledSalary(salaryPayment.getEmployerCbu(), salaryPayment.getEmployeeCbu(),
                    salaryPayment.getSalary());
            salaryPayment.setStatus(ScheduledTransactionStatus.DONE);

        } catch (AccountCBUNotFoundException ex) {
            LOGGER.error(ex.getLocalizedMessage());
            salaryPayment.setStatus(ScheduledTransactionStatus.ERROR);
            salaryPayment.setFailureCode("CBU_NOT_FOUND");
            salaryPayment.setFailureMessage(ex.getLocalizedMessage());

        } catch (InsufficientBalanceException ex) {
            LOGGER.error(ex.getLocalizedMessage());
            salaryPayment.setStatus(ScheduledTransactionStatus.ERROR);
            salaryPayment.setFailureCode("BUSINESS_ACCOUNT_INSUFFICIENT_FUNDS");
            salaryPayment.setFailureMessage("La cuenta CBU del negocio no posee fondos suficientes");

        } finally {
            salaryPaymentRepository.save(salaryPayment);
            if (ScheduledTransactionStatus.ERROR.equals(salaryPayment.getStatus())) {
                LOGGER.warn("Could not pay salary id: {}", salaryPayment.getId());
            } else {
                LOGGER.info("Successfully payed salary id: {}", salaryPayment.getId());
            }
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

    private Date getScheduledDate() {
        Date currentDatePlusTwoDays = DateUtils.plusDays(new Date(), 2);
        return DateUtils.atStartOfDay(currentDatePlusTwoDays);
    }

    public List<SalaryPaymentFailure> getFailures(String employerCBU, String fromDate) {
        if (!legalUserService.existsByCBU(employerCBU))
            throw new BusinessCBUNotFoundException("CBU de empleador no encontrado");

        if (StringUtils.isNotEmpty(fromDate) && !DateUtils.isValid(fromDate))
            throw new InvalidDateFormatException("Formato de fecha inválido. El formato debe ser yyyy-MM-dd");

        List<SalaryPayment> payments;
        if (StringUtils.isEmpty(fromDate)) {
            payments = salaryPaymentRepository.findAllByStatus(ScheduledTransactionStatus.ERROR.getValue());
        } else {
            payments = salaryPaymentRepository.findAllByStatusFromScheduledDate(ScheduledTransactionStatus.ERROR.getValue(),
                    DateUtils.parse(fromDate));
        }

        return payments.stream()
                .map(payment -> {
                    Resource resource = new Resource(payment.getEmployeeCbu(), DateUtils.format(payment.getScheduledDate()));
                    return new SalaryPaymentFailure(resource, payment.getFailureCode(), payment.getFailureMessage());
                })
                .collect(Collectors.toList());
    }
}
