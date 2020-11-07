package com.banco.api.published.controller;

import com.banco.api.exception.*;
import com.banco.api.published.request.collectService.CollectServiceRequest;
import com.banco.api.service.billService.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.banco.api.published.response.PublishedErrorResponseFactory.createPublishedErrorResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/collect-service")
public class CollectServiceController {

    private static final String SERVICE_PROVIDER_ACCOUNT_NOT_FOUND = "SERVICE_PROVIDER_ACCOUNT_NOT_FOUND";
    private static final String SERVICE_PROVIDER_NOT_FOUND = "SERVICE_PROVIDER_NOT_FOUND";
    private static final String ILLEGAL_ARGUMENT = "ILLEGAL_ARGUMENT";
    private static final String INVALID_DATE = "INVALID_DATE";
    private static final String CLIENT_NOT_FOUND = "CLIENT_NOT_FOUND";
    private static final String CLIENT_ACCOUNT_NOT_FOUND = "CLIENT_ACCOUNT_NOT_FOUND";
    private static final String DUPLICATED_SERVICE_ID = "DUPLICATED_SERVICE_ID";

    @Autowired
    private BillService billService;

    @PostMapping
    public ResponseEntity creditEntityDebitClients(@RequestBody CollectServiceRequest request) {
        try {
            billService.createPublishedBillServices(request);

        } catch (DuplicatedServiceIdException ex) {
            return createPublishedErrorResponse(HttpStatus.CONFLICT, DUPLICATED_SERVICE_ID, ex.getLocalizedMessage());

        } catch (BusinessCBUNotFoundException ex) {
            return createPublishedErrorResponse(HttpStatus.NOT_FOUND, SERVICE_PROVIDER_ACCOUNT_NOT_FOUND, ex.getLocalizedMessage());

        } catch (VendorNotFoundException ex) {
            return createPublishedErrorResponse(HttpStatus.NOT_FOUND, SERVICE_PROVIDER_NOT_FOUND, ex.getLocalizedMessage());

        } catch (ClientNotFoundException ex) {
            return createPublishedErrorResponse(HttpStatus.NOT_FOUND, CLIENT_NOT_FOUND, ex.getLocalizedMessage());

        } catch (ClientCBUNotFoundException ex) {
            return createPublishedErrorResponse(HttpStatus.NOT_FOUND, CLIENT_ACCOUNT_NOT_FOUND, ex.getLocalizedMessage());

        } catch (IllegalArgumentException ex) {
            return createPublishedErrorResponse(HttpStatus.BAD_REQUEST, ILLEGAL_ARGUMENT, ex.getLocalizedMessage());

        } catch (InvalidDateFormatException ex) {
            return createPublishedErrorResponse(HttpStatus.BAD_REQUEST, INVALID_DATE, ex.getLocalizedMessage());
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
