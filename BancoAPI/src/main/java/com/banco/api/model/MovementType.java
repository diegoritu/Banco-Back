package com.banco.api.model;

/*	Database meanings:
 * DEPOSIT = 0
 * EXTRACTION = 1
 * COMMISSION_OR_MAINTENANCE = 2
 * SERVICES_PAYMENT = 3
 */
public enum MovementType {
    DEPOSIT, EXTRACTION, COMMISSION_OR_MAINTENANCE, SERVICES_PAYMENT
}
