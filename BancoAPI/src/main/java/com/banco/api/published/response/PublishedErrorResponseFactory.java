package com.banco.api.published.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PublishedErrorResponseFactory {

    public static ResponseEntity createPublishedErrorResponse(HttpStatus httpStatus, String errorCode, String errorMessage) {
        return ResponseEntity
                .status(httpStatus)
                .body(new PublishedErrorMessageDTO(httpStatus.value(), errorCode, errorMessage));
    }
}
