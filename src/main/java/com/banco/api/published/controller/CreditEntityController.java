package com.banco.api.published.controller;

import com.banco.api.dto.movement.request.CreditEntityDebitClientsRequest;
import com.banco.api.dto.movement.request.CreditEntityDepositCommerceRequest;
import com.banco.api.dto.others.CreditEntityDebitClientsResponseDTO;
import com.banco.api.dto.others.CreditEntityDebitClientsResponseWithFailuresDTO;
import com.banco.api.exception.BusinessCBUNotFoundException;
import com.banco.api.exception.CommerceCBUNotFoundException;
import com.banco.api.exception.CreditEntityAccountInsuficientFundsException;
import com.banco.api.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.banco.api.published.response.PublishedErrorResponseFactory.createPublishedErrorResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/credit-entity")
public class CreditEntityController {

    @Autowired
    private MovementService movementService;

    @PostMapping("/deposit-commerce")
    public ResponseEntity creditEntityDepositCommerce(@RequestBody CreditEntityDepositCommerceRequest request){
        try {
            movementService.creditEntityDepositCommerce(request);
            return new ResponseEntity<Void> (HttpStatus.NO_CONTENT);
        }
        catch(CreditEntityAccountInsuficientFundsException ex){
            return createPublishedErrorResponse(HttpStatus.CONFLICT, "CREDIT_ENTITY_ACCOUNT_INSUFFICIENT_FUNDS", ex.getLocalizedMessage());
        }
        catch(BusinessCBUNotFoundException ex) {
            return createPublishedErrorResponse(HttpStatus.NOT_FOUND, "CREDIT_ENTITY_CBU_NOT_FOUND", ex.getLocalizedMessage());
        }
        catch(CommerceCBUNotFoundException ex) {
            return createPublishedErrorResponse(HttpStatus.NOT_FOUND, "BUSINESS_CBU_NOT_FOUND", ex.getLocalizedMessage());
        }
    }
}
