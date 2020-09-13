package com.banco.api.model.user;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.*;
import javax.xml.bind.DatatypeConverter;


@Entity
@Table(name = "users")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="userType", discriminatorType = DiscriminatorType.STRING)
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int idUser;
	/*	Database meanings for userType:
	 * PHYSICAL = 0
	 * LEGAL = 1
	 * ADMINISTRATIVE = 2
	 */
    protected int userTypeNumber;
    
    protected String cuitCuilCdi;
    
    @Column(name = "usr")
    protected String username;
    
    protected String address;
    protected String phone;
    protected boolean active;
    protected boolean firstLogin;
    protected String password;

    public User() {
    	this.active = true;
        this.firstLogin = true;
        this.password = generatePassword();
    }

    public User(String cuitCuilCdi, String username, String address, String phone, String mobilePhone) {
        super();
        this.cuitCuilCdi = cuitCuilCdi;
        this.username = username;
        this.address = address;
        this.phone = phone;
        this.active = true;
        this.firstLogin = true;
        this.password = generatePassword();
    }

	public User(int id, String cuitCuilCdi, String username, String address, String phone,
                boolean active) {
        super();
        this.idUser = id;
        this.cuitCuilCdi = cuitCuilCdi;
        this.username = username;
        this.address = address;
        this.phone = phone;
        this.active = active;
        this.firstLogin = true;
        this.password = generatePassword();
    }

	private String generatePassword() {
    	final int BASE_LENGHT = 10;
    	int randomAdditional = new Random().nextInt(6);
    	String lettersLower = "abcdefghijklmnopqrstuvwxyz";
    	String lettersUpper = lettersLower.toUpperCase();
    	String numbers = "123456789";
    	//String symbols = "!#$&/()=?*"; 
    	String joinOfThings = lettersLower + lettersUpper + numbers /*+ simbols*/;
    	List<String> letters = Arrays.asList(joinOfThings.split(""));
    	Collections.shuffle(letters);
    	String pass = letters.stream().collect(Collectors.joining()).substring(0, BASE_LENGHT + randomAdditional);
    	
        return pass;
    }
    
    public void hashPassword(String pass) {
    	String hashedPass = null;
    	MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(pass.getBytes());
	        byte[] digest = md.digest();
	        hashedPass = DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setPassword(hashedPass);
    }
	
    public int getId() {
        return idUser;
    }

    public void setId(int id) {
        this.idUser = id;
    }

    public int getUserType() {
        return userTypeNumber;
    }

    public void setUserType(int userType) {
        this.userTypeNumber = userType;
    }

    public String getCuitCuilCdi() {
        return cuitCuilCdi;
    }

    public void setCuitCuilCdi(String cuitCuilCdi) {
        this.cuitCuilCdi = cuitCuilCdi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", userType=" + userTypeNumber +
                ", cuitCuilCdi='" + cuitCuilCdi + '\'' +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", active=" + active +
                '}';
    }
	
	public void resetPassword() {
		
		this.password = generatePassword();
		this.firstLogin = true;
		
	}


}
