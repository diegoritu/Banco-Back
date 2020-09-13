package com.banco.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFactory {

    public static ResponseEntity createErrorResponseEntity(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body("{\"error\": \"" + message + "\"}");
    }
}
