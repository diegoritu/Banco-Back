package com.banco.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFactory {

    public static ResponseEntity createErrorResponseEntity(String message, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body("{\"error\": \"" + message + "\"}");
    }
}
