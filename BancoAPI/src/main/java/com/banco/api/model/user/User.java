package com.banco.api.model.user;

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
    private int idUser;
	/*	Database meanings for userType:
	 * PHYSICAL = 0
	 * LEGAL = 1
	 * ADMINISTRATIVE = 2
	 */
    protected int userType;
    
    private String cuitCuilCdi;
    private String username;
    private String address;
    private String phone;
    private boolean active;

    public User() {
    }

    public User(String cuitCuilCdi, String username, String address, String phone) {
        super();
        this.cuitCuilCdi = cuitCuilCdi;
        this.username = username;
        this.address = address;
        this.phone = phone;
        this.active = true;
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
    }

    public int getId() {
        return idUser;
    }

    public void setId(int id) {
        this.idUser = id;
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

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", userType=" + userType +
                ", cuitCuilCdi='" + cuitCuilCdi + '\'' +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", active=" + active +
                '}';
    }
}
