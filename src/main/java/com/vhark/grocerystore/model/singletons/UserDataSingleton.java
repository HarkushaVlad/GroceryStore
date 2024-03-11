package com.vhark.grocerystore.model.singletons;

public class UserDataSingleton {

    private static final UserDataSingleton instance = new UserDataSingleton();

    private String idCode;

    private Boolean isEmployee;

    private UserDataSingleton(){};

    public static UserDataSingleton getInstance() {
        return instance;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public Boolean getIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(Boolean employee) {
        isEmployee = employee;
    }
}
