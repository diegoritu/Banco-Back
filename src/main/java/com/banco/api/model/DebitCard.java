package com.banco.api.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.banco.api.dto.others.DebitCardDTO;
import com.banco.api.model.account.Savings;

@Entity
@Table(name = "debit_card")
public class DebitCard {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int debitCardId;
	@Column
    @Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;
	private String number;
	private String securityCode;
	@OneToOne
	@JoinColumn(name = "idSavingsAccount")
	private Savings savingsAccount;
	
	public DebitCard() {}
	
	public DebitCard(Savings savingsAccount) {
		super();
		this.savingsAccount = savingsAccount;
		generateExpirationDate();
		generateNumber();
		generateSecurityCode();
	}
	
	private void generateExpirationDate() {
		Calendar exp = Calendar.getInstance();
		exp.add(Calendar.YEAR, 4);
		Random r = new Random();
		int month = r.nextInt((12-1) + 1);
		exp.set(Calendar.MONTH, month);
		this.expirationDate = exp.getTime();
	}

	private void generateSecurityCode() {
		Random r = new Random();
		int code = r.nextInt((999 - 100) + 1) + 100;
		this.securityCode = Integer.toString(code);
	}

	public void generateNumber() {
		String begining = "44"; //[0] = Bank, [1] = Visa
		String restOfTheNumber = Long.toString(Math.abs(ThreadLocalRandom.current().nextLong(10000000000000L,99999999999999L)));
		String number = begining + restOfTheNumber;
		this.number = number;
	}
	
	public int getId() {
		return debitCardId;
	}
	public void setId(int id) {
		this.debitCardId = id;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getNumber() {
		return number;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public Savings getSavingsAccount() {
		return savingsAccount;
	}
	public void setSavingsAccount(Savings savingsAccount) {
		this.savingsAccount = savingsAccount;
	}
	
	@Override
	public String toString() {
		return "DebitCard [id=" + debitCardId + ", expirationDate=" + expirationDate + ", number=" + number + ", securityCode="
				+ securityCode + ", savingsAccount=" + savingsAccount + "]";
	}
	
	@SuppressWarnings("deprecation")
	public DebitCardDTO toView() {
		DebitCardDTO result = new DebitCardDTO();
		String month;
		String year = Integer.toString(this.getExpirationDate().getYear() % 100);
		result.setNumber(this.getNumber());
		result.setSecurityCode(this.getSecurityCode());
		month = this.getExpirationDate().getMonth() < 10 ? 0 + Integer.toString(this.getExpirationDate().getMonth()) : Integer.toString(this.getExpirationDate().getMonth());
		String exp = month + "/" + year;
		result.setExpirationDate(exp);
		result.setSavingsAccount(this.getSavingsAccount().toView());
		return result;
	}
	
	
}
