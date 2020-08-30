package com.banco.api.dto.user;

public class AdministrativeUserDTO extends UserDTO {

    private String dni;
    private String birthDate;
    private String name;
    private String mobilePhone;

    public AdministrativeUserDTO() {
    }

    public AdministrativeUserDTO(int userId, String userType, String cuitCuilCdi, String username, String address,
                                 String phone, boolean active,
                                 String dni, String birthDate, String name, String mobilePhone) {
        super(userId, userType, cuitCuilCdi, username, address, phone, active);
        this.dni = dni;
        this.birthDate = birthDate;
        this.name = name;
        this.mobilePhone = mobilePhone;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Override
    public String toString() {
        return "AdministrativeUserDTO{" +
                "dni='" + dni + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", name='" + name + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                '}';
    }
}
