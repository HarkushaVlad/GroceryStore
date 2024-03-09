package com.vhark.grocerystore.util;

import java.util.regex.Pattern;

public final class userDataValidator {
    private userDataValidator() {};

    public static boolean validateUserName(String userName) {
        return userName.length() >= 2 && Pattern.matches("[A-Za-zА-Яа-яїє']+", userName);
    }

    public static boolean validateUserAddress(String address) {
        return address.length() >= 5;
    }

    public static boolean validateUserIdCode(String idCode) {
        return idCode.matches("\\d{8}-\\d{5}");
    }

    public static boolean validateUserPassword(String password) {
        return password.length() > 5 || password.matches(".*\\d.*");
    }

    public static boolean comparePasswords(String firstPassword, String secondPassword) {
        return firstPassword.equals(secondPassword);
    }
}
