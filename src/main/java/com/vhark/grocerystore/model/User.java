package com.vhark.grocerystore.model;

public class User {
    private String firstName;
    private String lastName;
    private String middleName;
    private String idCode;
    private String address;
    private String passwordHash;
    private boolean isEmployee;

    public User(String firstName, String lastName, String middleName,
                String idCode, String address, String passwordHash, boolean isEmployee) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.idCode = idCode;
        this.address = address;
        this.passwordHash = passwordHash;
        this.isEmployee = isEmployee;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isEmployee() {
        return isEmployee;
    }

    public void setEmployee(boolean employee) {
        isEmployee = employee;
    }
}
