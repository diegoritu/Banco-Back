package com.banco.api.model;

public enum MovementType {
	Deposit, Extraction, CommissionOrMaintenance, ServicesPay;
	
	/*	Database meanings:
	 * Deposit = 0
	 * Extraction = 1
	 * CommissionOrMaintenance = 2
	 * ServicesPay = 3
	 */
}
