package com.banco.api.dto.movement.request;

import com.banco.api.dto.others.request.DebitCardRequest;

public class DebitCardPaymentRequest {
	private String businessCbu;
	DebitCardRequest debitCard;
	private float amount;
	private String concept;
	
	public DebitCardPaymentRequest() {}

	public DebitCardPaymentRequest(String businessCbu, DebitCardRequest debitCard, float amount, String concept) {
		super();
		this.businessCbu = businessCbu;
		this.debitCard = debitCard;
		this.amount = amount;
		this.concept = concept;
	}

	public String getBusinessCbu() {
		return businessCbu;
	}
	public DebitCardRequest getDebitCard() {
		return debitCard;
	}

	public float getAmount() {
		return amount;
	}
	public String getConcept() {
		return concept;
	}

	@Override
	public String toString() {
		return "DebitCardPaymentRequest [businessCbu=" + businessCbu + ", debitCard=" + debitCard + ", amount=" + amount
				+ ", concept=" + concept + "]";
	}
}
