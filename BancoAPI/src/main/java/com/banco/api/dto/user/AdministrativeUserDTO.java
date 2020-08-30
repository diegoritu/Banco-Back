package com.banco.api.dto.user;

public class AdministrativeUserDTO extends UserDTO {

    private String dni;
    private String birthDate;
    private String firstName;
    private String lastName;
    private String mobilePhone;

    public AdministrativeUserDTO() {
    }

    public AdministrativeUserDTO(int userId, String userType, String cuitCuilCdi, String username, String address,
                                 String phone, boolean active,
                                 String dni, String birthDate, String firstName, String lastName, String mobilePhone) {
        super(userId, userType, cuitCuilCdi, username, address, phone, active);
        this.dni = dni;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                '}';
    }
}
