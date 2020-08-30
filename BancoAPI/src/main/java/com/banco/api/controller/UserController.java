package com.banco.api.controller;

import com.banco.api.dto.request.LegalUserRequest;
import com.banco.api.dto.request.PhysicalUserRequest;
import com.banco.api.dto.user.LegalUserDTO;
import com.banco.api.dto.user.response.PhysicalUserDTO;
import com.banco.api.service.user.LegalUserService;
import com.banco.api.service.user.PhysicalUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private PhysicalUserService physicalUserService;
    @Autowired
    private LegalUserService legalUserService;

    @PostMapping("/physical")
    public ResponseEntity<PhysicalUserDTO> createPhysical(@RequestBody PhysicalUserRequest request) {
        LOGGER.info("Creating physical user {}", request.toString());
        return new ResponseEntity<>(physicalUserService.createUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/legal")
    public ResponseEntity<LegalUserDTO> createLegal(@RequestBody LegalUserRequest request) {
        LOGGER.info("Creating legal user {}", request.toString());
        return new ResponseEntity<>(legalUserService.createUser(request), HttpStatus.CREATED);
    }

}