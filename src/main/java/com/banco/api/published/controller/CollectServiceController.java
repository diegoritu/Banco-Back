package com.banco.api.published.controller;

import com.banco.api.exception.*;
import com.banco.api.published.request.collectService.CollectServiceRequest;
import com.banco.api.published.response.collectService.list.CollectServiceItem;
import com.banco.api.service.billService.BillService;
import com.banco.api.task.CollectServiceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.banco.api.published.response.PublishedErrorResponseFactory.createPublishedErrorResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/collect-service")
public class CollectServiceController {

    private static final String SERVICE_PROVIDER_ACCOUNT_NOT_FOUND = "SERVICE_PROVIDER_ACCOUNT_NOT_FOUND";
    private static final String ILLEGAL_ARGUMENT = "ILLEGAL_ARGUMENT";
    private static final String INVALID_DATE = "INVALID_DATE";
    private static final String CLIENT_NOT_FOUND = "CLIENT_NOT_FOUND";
    private static final String CLIENT_ACCOUNT_NOT_FOUND = "CLIENT_ACCOUNT_NOT_FOUND";
    private static final String DUPLICATED_SERVICE_ID = "DUPLICATED_SERVICE_ID";

    private static final String PROVIDER_NOT_FOUND = "PROVIDER_NOT_FOUND";

    private static final Logger LOGGER = LoggerFactory.getLogger(CollectServiceController.class);

    @Autowired
    private BillService billService;
    @Autowired
    private CollectServiceTask collectServiceTask;

    @PostMapping
    public ResponseEntity createBillServices(@RequestBody CollectServiceRequest request) {
        try {
            return new ResponseEntity<>(billService.createPublishedBillServices(request), HttpStatus.CREATED);

        } catch (DuplicatedServiceIdException ex) {
            return createPublishedErrorResponse(HttpStatus.CONFLICT, DUPLICATED_SERVICE_ID, ex.getLocalizedMessage());

        } catch (BusinessCBUNotFoundException ex) {
            return createPublishedErrorResponse(HttpStatus.NOT_FOUND, SERVICE_PROVIDER_ACCOUNT_NOT_FOUND, ex.getLocalizedMessage());

        } catch (ClientNotFoundException ex) {
            return createPublishedErrorResponse(HttpStatus.NOT_FOUND, CLIENT_NOT_FOUND, ex.getLocalizedMessage());

        } catch (ClientCBUNotFoundException ex) {
            return createPublishedErrorResponse(HttpStatus.NOT_FOUND, CLIENT_ACCOUNT_NOT_FOUND, ex.getLocalizedMessage());

        } catch (IllegalArgumentException ex) {
            return createPublishedErrorResponse(HttpStatus.BAD_REQUEST, ILLEGAL_ARGUMENT, ex.getLocalizedMessage());

        } catch (InvalidDateFormatException ex) {
            return createPublishedErrorResponse(HttpStatus.BAD_REQUEST, INVALID_DATE, ex.getLocalizedMessage());
        }
    }

    @GetMapping("/force")
    private ResponseEntity executeCollectServiceTask() {
        collectServiceTask.execute();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/list")
    private ResponseEntity getCollectServices(@RequestParam(required = true) String serviceProviderId,
                                              @RequestParam(required = false) String fromDueDate) {
        LOGGER.info("Listing service payment status items");
        try {
            List<CollectServiceItem> items = billService.getCollectServices(serviceProviderId, fromDueDate);
            LOGGER.info("Service payment status items: " + items.size());
            return new ResponseEntity<>(items, HttpStatus.OK);

        } catch (VendorNotFoundException ex) {
            return createPublishedErrorResponse(HttpStatus.NOT_FOUND, PROVIDER_NOT_FOUND, ex.getLocalizedMessage());

        } catch (InvalidDateFormatException ex) {
            return createPublishedErrorResponse(HttpStatus.BAD_REQUEST, INVALID_DATE, ex.getLocalizedMessage());
        }
    }
}
