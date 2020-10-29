package com.banco.api.published.controller;

import com.banco.api.dto.movement.request.DebitCardPaymentRequest;
import com.banco.api.dto.others.TransactionIdDTO;
import com.banco.api.exception.BusinessCBUNotFoundException;
import com.banco.api.exception.ClientInsuficientFundsException;
import com.banco.api.exception.DebitCardNotFoundException;
import com.banco.api.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.banco.api.published.response.PublishedErrorResponseFactory.createPublishedErrorResponse;

@RestController
@RequestMapping("/debit-card")
public class DebitCardController {

    @Autowired
    private MovementService movementService;

    @PostMapping("/payment")
    public ResponseEntity debitCardPayment(@RequestBody DebitCardPaymentRequest request){
        try {
            TransactionIdDTO response = new TransactionIdDTO();
            response.setTransactionId(movementService.debitCardPayment(request));
            return new ResponseEntity<TransactionIdDTO> (response, HttpStatus.OK);
        }
        catch(ClientInsuficientFundsException ex){
            return createPublishedErrorResponse(HttpStatus.CONFLICT, "CLIENT_INSUFFICIENT_FUNDS",
                    "El cliente no tiene fondos para realizar la operación");
        }
        catch(DebitCardNotFoundException ex) {
            return createPublishedErrorResponse(HttpStatus.NOT_FOUND, "DEBIT_CARD_NOT_FOUND",
                    "La tarjeta de débito " + request.getDebitCard().getNumber() + " no existe o se ingresó mal algún dato adicional de la misma.");
        }
        catch(BusinessCBUNotFoundException ex) {
            return createPublishedErrorResponse(HttpStatus.NOT_FOUND, "BUSINESS_CBU_NOT_FOUND",
                    "El CBU del comercio " + request.getBusinessCbu() + " no existe.");
        }
    }
}
