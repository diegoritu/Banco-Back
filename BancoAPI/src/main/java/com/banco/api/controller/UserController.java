package com.banco.api.controller;

import com.banco.api.dto.user.PhysicalUserDTO;
import com.banco.api.service.UserService;
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
    private UserService userService;

    @PostMapping("/physical")
    public ResponseEntity createPhysical(@RequestBody PhysicalUserDTO physicalUserView) {
        LOGGER.info("Creating physical user: {}", physicalUserView);
        return new ResponseEntity<>(userService.createPhysical(physicalUserView), HttpStatus.CREATED);
    }
}
