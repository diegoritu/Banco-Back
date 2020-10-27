package com.banco.api.published.controller;

import com.banco.api.exception.BusinessCBUNotFoundException;
import com.banco.api.exception.EmployeeCBUNotFoundException;
import com.banco.api.exception.InsufficientBalanceException;
import com.banco.api.exception.InvalidDateFormatException;
import com.banco.api.published.request.SalaryPaymentRequest;
import com.banco.api.service.SalaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.banco.api.published.response.PublishedErrorResponseFactory.createPublishedErrorResponse;

@RestController
@RequestMapping("/salary-payment")
public class SalaryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalaryController.class);
    private static final String EMPLOYER_CBU_NOT_FOUND = "EMPLOYER_CBU_NOT_FOUND";
    private static final String EMPLOYEE_CBU_NOT_FOUND = "EMPLOYEE_CBU_NOT_FOUND";
    private static final String EMPLOYER_INSUFFICIENT_FUNDS = "EMPLOYER_INSUFFICIENT_FUNDS";

    private static final String BUSINESS_CBU_NOT_FOUND = "BUSINESS_CBU_NOT_FOUND";
    private static final String INVALID_DATE_FORMAT = "INVALID_DATE_FORMAT";

    @Autowired
    private SalaryService salaryService;

    @PostMapping()
    public ResponseEntity createSalaryPayment(@RequestBody SalaryPaymentRequest request) {
        LOGGER.info("Salary payment request: {}", request.toString());
        try {
            salaryService.saveSalaryRequest(request);
        } catch (BusinessCBUNotFoundException ex) {
            return createPublishedErrorResponse(HttpStatus.NOT_FOUND, EMPLOYER_CBU_NOT_FOUND,
                    ex.getLocalizedMessage());

        } catch (EmployeeCBUNotFoundException ex) {
            return createPublishedErrorResponse(HttpStatus.NOT_FOUND, EMPLOYEE_CBU_NOT_FOUND,
                    ex.getLocalizedMessage());

        } catch (InsufficientBalanceException ex) {
            return createPublishedErrorResponse(HttpStatus.CONFLICT, EMPLOYER_INSUFFICIENT_FUNDS,
                    ex.getLocalizedMessage());
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
    fromDate format: yyyy-mm-dd
     */
    @GetMapping("/failures")
    public ResponseEntity getFailures(@RequestParam(required = true) String employerCBU,
                                      @RequestParam(required = false) String fromDate) {
        try {
            return new ResponseEntity<>(salaryService.getFailures(employerCBU, fromDate), HttpStatus.OK);
        } catch (BusinessCBUNotFoundException ex) {
            return createPublishedErrorResponse(HttpStatus.NOT_FOUND, BUSINESS_CBU_NOT_FOUND, ex.getLocalizedMessage());

        } catch (InvalidDateFormatException ex) {
            return createPublishedErrorResponse(HttpStatus.BAD_REQUEST, INVALID_DATE_FORMAT, ex.getLocalizedMessage());
        }
    }
}
