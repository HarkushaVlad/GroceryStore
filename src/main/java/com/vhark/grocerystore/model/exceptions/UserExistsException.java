package com.vhark.grocerystore.model.exceptions;

import java.sql.SQLException;

public class UserExistsException extends SQLException {
    public UserExistsException(String message) {
        super(message);
    }
}
