package com.banco.api.model.user;

import javax.persistence.*;

import com.banco.api.adapter.DateUtils;
import com.banco.api.dto.user.PhysicalUserDTO;
import com.banco.api.dto.user.UserDTO;
import com.banco.api.dto.user.UserType;

@Entity
@Table(name = "users")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="userType", discriminatorType = DiscriminatorType.STRING)
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;
	/*	Database meanings for userType:
	 * PHYSICAL = 0
	 * LEGAL = 1
	 * ADMINISTRATIVE = 2
	 */
    protected int userTypeNumber;
    
    private String cuitCuilCdi;
    
    @Column(name = "usr")
    private String username;
    
    private String address;
    private String phone;
    protected boolean active;

    public User() {
    }

    public User(String cuitCuilCdi, String username, String address, String phone, String mobilePhone) {
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
    


}
