package com.banco.api.published.controller;

import com.banco.api.exception.BusinessCBUNotFoundException;
import com.banco.api.exception.VendorNotFoundException;
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
    private static final String EMPTY_SERVICES = "EMPTY_SERVICES";

    @Autowired
    private BillService billService;

    @PostMapping
    public ResponseEntity creditEntityDebitClients(@RequestBody CollectServiceRequest request) {
        try {
            billService.createPublishedBillServices(request);
        } catch (BusinessCBUNotFoundException ex) {
            return createPublishedErrorResponse(HttpStatus.NOT_FOUND, SERVICE_PROVIDER_ACCOUNT_NOT_FOUND,
                    ex.getLocalizedMessage());

        } catch (VendorNotFoundException ex) {
            return createPublishedErrorResponse(HttpStatus.NOT_FOUND, SERVICE_PROVIDER_NOT_FOUND,
                    ex.getLocalizedMessage());

        } catch (IllegalArgumentException ex) {
            return createPublishedErrorResponse(HttpStatus.NOT_FOUND, EMPTY_SERVICES, ex.getLocalizedMessage());
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
