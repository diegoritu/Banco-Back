package com.banco.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idUsr;
	/*	Database meanings for userType:
	 * PHYSICAL = 0
	 * LEGAL = 1
	 * ADMINISTRATIVE = 2
	 */
    private int userType;
    
    private String cuitCuilCdi;
    private String usr;
    private String address;
    private String phone;
    private boolean active;

    public User(int id, String cuitCuilCdi, String usr, String address, String phone,
                boolean active) {
        super();
        this.idUsr = id;
        this.cuitCuilCdi = cuitCuilCdi;
        this.usr = usr;
        this.address = address;
        this.phone = phone;
        this.active = active;
    }

    public int getId() {
        return idUsr;
    }

    public void setId(int id) {
        this.idUsr = id;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getCuitCuilCdi() {
        return cuitCuilCdi;
    }

    public void setCuitCuilCdi(String cuitCuilCdi) {
        this.cuitCuilCdi = cuitCuilCdi;
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

    public void ay() {
        System.out.println("EYYY");
    }
}
