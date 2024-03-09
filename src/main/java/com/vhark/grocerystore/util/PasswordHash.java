package com.vhark.grocerystore.util;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordHash {
    private PasswordHash() {};

    public static String generateHash(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }
}
