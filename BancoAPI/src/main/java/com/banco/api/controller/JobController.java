package com.banco.api.controller;

import com.banco.api.service.account.InterestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    InterestsService interestRateService;

    //Testing
    @ResponseBody
    @RequestMapping(value = "/savings-month-interests", method = {RequestMethod.GET})
    public ResponseEntity deactivate() {
        interestRateService.applyInterestRate();
        return new ResponseEntity(HttpStatus.OK);
    }
}
