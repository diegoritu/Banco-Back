package com.banco.api.entities;

import java.util.Date;

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

import com.banco.api.model.UserType;

@Entity
@Table(name="users")
public class UserEntity {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int idUser;
	
	private String cuitCuilCdi;
	private int userType;
	private String usr;
	private String address;
	private String phone;
	private boolean active;
	private String dni;
	
	@OneToOne
	@JoinColumn(name="idSavingsAccount")
	@Column(name="idSavingsAccount")
	private SavingsEntity savingsAccount;
	
	@OneToOne
	@JoinColumn(name="idCheckingAccount")
	@Column(name="idCheckingAccount")
	private CheckingEntity checkingAccount;
	
	@Temporal(TemporalType.TIMESTAMP)	
	private Date birthday;

	private String name;
	private String businessName;
	public UserEntity(int idUser, String cuitCuilCdi, int userType, String usr, String address, String phone,
			boolean active, String dni, SavingsEntity savingsAccount, CheckingEntity checkingAccount, Date birthday,
			String name, String businessName) {
		super();
		this.idUser = idUser;
		this.cuitCuilCdi = cuitCuilCdi;
		this.userType = userType;
		this.usr = usr;
		this.address = address;
		this.phone = phone;
		this.active = active;
		this.dni = dni;
		this.savingsAccount = savingsAccount;
		this.checkingAccount = checkingAccount;
		this.birthday = birthday;
		this.name = name;
		this.businessName = businessName;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public String getCuitCuilCdi() {
		return cuitCuilCdi;
	}
	public void setCuitCuilCdi(String cuitCuilCdi) {
		this.cuitCuilCdi = cuitCuilCdi;
	}
	public UserType getUserType() {
		UserType ut = null;
		if(userType == 0) {
			ut = UserType.Physical;
		}
		if(userType == 1) {
			ut = UserType.Legal;
		}
		if(userType == 2) {
			ut = UserType.Administrative;
		}
		return ut;
	}
	public void setUserType(UserType ut) {
		if(UserType.Physical == ut) {
			this.userType = 0;
		}
		if (UserType.Legal == ut) {
			this.userType = 1;
		}
		if (UserType.Administrative == ut) {
			this.userType = 2;
		}
	}
	public String getUsr() {
		return usr;
	}
	public void setUsr(String usr) {
		this.usr = usr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public SavingsEntity getSavingsAccount() {
		return savingsAccount;
	}
	public void setSavingsAccount(SavingsEntity savingsAccount) {
		this.savingsAccount = savingsAccount;
	}
	public CheckingEntity getCheckingAccount() {
		return checkingAccount;
	}
	public void setCheckingAccount(CheckingEntity checkingAccount) {
		this.checkingAccount = checkingAccount;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
}
