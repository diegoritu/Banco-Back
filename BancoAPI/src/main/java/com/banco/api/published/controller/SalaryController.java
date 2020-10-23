package com.banco.api.published.controller;

import com.banco.api.published.request.SalaryPaymentRequest;
import com.banco.api.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PostMapping("/payment")
    public ResponseEntity salaryPayment(@RequestBody SalaryPaymentRequest request) {
        salaryService.saveSalaryRequest(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
