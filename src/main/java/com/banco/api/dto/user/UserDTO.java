package com.banco.api.dto.user;

public abstract class UserDTO {

    private int id;
    private String userType;
    private String cuitCuilCdi;
    private String username;
    private String address;
    private String phone;
    private boolean active;

    public UserDTO() {
    }

    public UserDTO(int id, String userType, String cuitCuilCdi, String username, String address, String phone,
                   boolean active) {
        this.id = id;
        this.userType = userType;
        this.cuitCuilCdi = cuitCuilCdi;
        this.username = username;
        this.address = address;
        this.phone = phone;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
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
}
